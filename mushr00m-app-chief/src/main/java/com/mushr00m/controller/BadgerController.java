package com.mushr00m.controller;

import com.huaban.analysis.jieba.JiebaSegmenter;
import com.huaban.analysis.jieba.SegToken;
import com.mushr00m.entity.TbProduct;
import com.mushr00m.entity.TbRoom;
import com.mushr00m.entity.TbType;
import com.mushr00m.model.LogoAddress;
import com.mushr00m.model.SysUser;
import com.mushr00m.service.*;
import com.mushr00m.utils.BaseController;
import com.mushr00m.utils.JwtUtils;
import com.mushr00m.utils.mushr00mUtils.RedisKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import redis.clients.jedis.Jedis;
import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/badger")
public class BadgerController extends BaseController {

    @Autowired
    private TbTypeService tbTypeService;

    @Autowired
    private TbProductService tbProductService;

    @Autowired
    private TbRoomService tbRoomService;

    @Autowired
    private TbOrderService tbOrderService;

    @Value("${Nginx.address}")
    private String NginxPicAddress;

    Jedis jedis = new Jedis("127.0.0.1",6379);


    ////查询功能区

    ///商城页面查询

    //查询所有商品类型
    @RequestMapping("/selectAllType")
    @ResponseBody
    public List<TbType> selectAllType(){
        return tbTypeService.selectAllType();
    }


    //查询所有上架商品
    @RequestMapping("/selectAllProduct")
    @ResponseBody
    public List<TbProduct> selectAllProduct(){
        List<TbProduct> list = tbProductService.selectAllProduct().stream()
                .filter(o->o.getState().toString().equals("1"))
                .collect(Collectors.toList());

                list.forEach(o->{o.setImg(NginxPicAddress+o.getImg());});
                return list;
    }

    //对所有商品（上架和下架的）进行分词
    @RequestMapping("/fengCiData")
    @ResponseBody
    public String fengCiData(HttpServletRequest req){

        String token = super.getCookieVal(req,"token");
        SysUser user = JwtUtils.getObject(token,SysUser.class);

        //在完善一条商品数据之后将当前登录用户所有的无效商品数据删除
        TbProduct tbr = new TbProduct();
        tbr.setUserid(user.getId().intValue());
        tbr.setName("RUBBISH");

        List<TbProduct> tbt = tbProductService.selectByUserId(user.getId().intValue());
        tbt.stream().forEach(o->{
            if(o.getName().equals("RUBBISH") || o.getTypeid()==0){
                tbProductService.deleteRubbish(o);
            }
        });


        List<TbProduct> listFC1 = tbProductService.selectAllProduct();
        jedis.flushDB();
        listFC1.stream().forEach(o->{
            //获取商品类型名字
            int typeID = o.getTypeid();
            String type = tbTypeService.selectTypeNameById(typeID).getType();

            //对商品创建分词倒排索引
            JiebaSegmenter jieba = new JiebaSegmenter();
            List<SegToken> segTokenList = jieba.process(o.getName()+type+o.getAccount(), JiebaSegmenter.SegMode.INDEX);

            for(SegToken s:segTokenList){
                if(s.word.length()<2){
                    continue;
                }
                jedis.sadd(s.word,o.getId().toString());
                System.out.println("创建了"+o.getName()+"の索引");
            }
            });
        return "分词完毕";
        }

    //使用文本进行模糊查询
    @RequestMapping("/selectByText")
    @ResponseBody
    public List<TbProduct> selectByText(String text){
        return tbProductService.selectByText(text).stream()
                .filter(o->o.getState().toString().equals("1"))
                .collect(Collectors.toList());
    }

    //商城页面的多条件查询
    @RequestMapping("/selectByCond")
    @ResponseBody
    public List<TbProduct> selectByCond(String type, String cond,String text){
        if(text.equals("")) {
            List<TbProduct> list =  tbProductService.selectByCond(type, cond).stream()
                    .filter(o->o.getState().toString().equals("1"))
                    .collect(Collectors.toList());
            list.stream().forEach(o->{o.setImg(NginxPicAddress+o.getImg());});
            return list;
        }

        //对模糊搜索的关键词进行分词
        List<TbProduct> llistB = new ArrayList<>();
        JiebaSegmenter jiebaTextB = new JiebaSegmenter();
        jiebaTextB.process(text, JiebaSegmenter.SegMode.SEARCH).stream().forEach(t->{
            Set<String> ids = jedis.smembers(t.word);
            for(String id:ids){
                llistB.add(tbProductService.selectById(id));
            }
        });

        llistB.stream().forEach(o->{o.setImg(NginxPicAddress+o.getImg());});

        //对模糊搜索获得的数据
        if(!type.equals("0")) {
            List<TbProduct> listDE = llistB.stream().filter(o -> o.getTypeid().toString().equals(type) &&
                    o.getState().toString().equals("1"))
                    .collect(Collectors.toList());
            if (cond.equals("0") || cond.equals("1")) {
                return listDE.stream().sorted(Comparator.comparing(TbProduct::getCreatetime))
                        .collect(Collectors.toList());
            } else {
                return listDE.stream().sorted(Comparator.comparing(TbProduct::getCreatetime).reversed())
                        .collect(Collectors.toList());
            }
        }


        if (cond.equals("0") || cond.equals("1")) {
            return llistB.stream().filter(o->o.getState().toString().equals("1"))
                    .sorted(Comparator.comparing(TbProduct::getCreatetime)).collect(Collectors.toList());
        } else {
            return llistB.stream().filter(o->o.getState().toString().equals("1"))
                    .sorted(Comparator.comparing(TbProduct::getCreatetime).reversed()).collect(Collectors.toList());
        }

    }


    //通过商品id查出该商品的所有竞拍室[上线的]
    @RequestMapping("/GoToRoomList")
    @ResponseBody
    public List<TbRoom> GoToRoomList(int id){
        return tbRoomService.selectByPID(id);
    }

    //配套GoToRoomList 跳转到roomList页面
    @RequestMapping("/goToRoomView")
    public String goToRoomView(){
        return "mushr00m/badger/roomlist";
    }

    ///商品详情(roomList)页面查询

    //商品详情内查询竞拍室列表
    @RequestMapping("/roomList/selectByCond")
    @ResponseBody
    public List<TbRoom> selectByCond(String cond,String num1,String num2,String pid){

        int ppid = Integer.parseInt(pid);
        List<TbRoom> ListTR = tbRoomService.selectByPID(ppid);
        if(cond.equals("0") || cond.equals("1")){
                return ListTR.stream().sorted(Comparator.comparing(TbRoom::getCreatetime))
                        .collect(Collectors.toList());
            }
        List<TbRoom> ll =  ListTR.stream().sorted(Comparator.comparing(TbRoom::getCreatetime).reversed())
                .collect(Collectors.toList());
       return ll;
        }


    //商品详情内查具体商品
    @RequestMapping("/roomlist/selectProductByPID")
    @ResponseBody
    public List<TbProduct> selectProductByPID(String pid){
        List<TbProduct> list = tbProductService.selectProductByPID(pid);
        list.stream().forEach(o->{o.setImg(NginxPicAddress+o.getImg());});
        return list;
    }

    //从商品详情跳转到竞价室
    @RequestMapping("/toTbRoom")
    public String GoToRoom(){
        return "mushr00m/badger/tbroom";
    }

    ///竞拍室部分查询

    //竞拍室内查询竞拍室信息
    @RequestMapping("/TbRoom/getBasicRoomItems")
    @ResponseBody
    public List<TbRoom> getBasicRoomItems(@RequestParam("rid")int id){
        return tbRoomService.getBasicRoomItems(id);
    }

    //竞拍室内查询竞拍室信息[返回对象]
    @RequestMapping("/TbRoom/getBasicRoomItemsC")
    @ResponseBody
    public TbRoom getBasicRoomItemsC(@RequestParam("rid")int id){
        return tbRoomService.getBasicRoomItems(id).get(0);
    }



    //竞拍室内查询对应商品的信息
    @RequestMapping("/TbRoom/getBasicRoomProductItems")
    @ResponseBody
    public List<TbProduct> getBasicRoomProductItems(@RequestParam("rid")int id){
        List<TbProduct> list =  tbRoomService.getBasicRoomProductItems(id);
        list.stream().forEach(o->{o.setImg(NginxPicAddress+o.getImg());});
        return list;
    }

    //竞拍室获得竞拍结束时间
    @RequestMapping("/TbRoom/getEndTime")
    @ResponseBody
    public TbRoom getEndTime(@RequestParam("rid")int id){
        return tbRoomService.getBasicRoomItems(id).get(0);
    }

    ///竞拍逻辑

    //出价前判断出价是否合法
    @RequestMapping("/Tb/configMax")
    @ResponseBody
    public String configMax(@RequestParam("rid")int id,
                @RequestParam("myprice")Double price, HttpServletRequest req){
//        //拼装专用的redis库内竞拍室最高价lock
//        String redisPriceKey = "TRROOM"+id+"MAX";

      //使用redis完成竞拍时的阻塞式锁操作
        String redisIOKey = "REDISKEY";

        if(tbRoomService.getBasicRoomItems(id).get(0).getState()==0){
            return "over";
        }

        if(RedisKey.getLock(redisIOKey,jedis)) {

            //int max = Integer.parseInt(jedis.get(redisPriceKey));
            Double max = tbRoomService.getBasicRoomItems(id).get(0).getMaxprice();
            if (price <= max) {
                //出价不高于当前最高价
                return "0";
            } else {
                //用新的最高价覆盖redis内和竞拍室表内的记录

                //jedis.set(redisPriceKey, String.valueOf(price));
                int result = tbRoomService.updateMaxPrice(price, id);

                if (result == 0) {
                    //出价失败
                    return "2";
                }

                RedisKey.throwLock(redisIOKey,jedis);
                //出价成功
                String token = super.getCookieVal(req, "token");
                SysUser sysUser = JwtUtils.getObject(token, SysUser.class);

                int result2 = tbOrderService.recordPriceList(id, sysUser.getId().intValue(),
                        sysUser.getAccount(), price);
                if (result2 == 0) {
                    //出价日志记录失败
                    return "3";
                }

                //出价成功

                return "1";

            }

        }else{
            //出价被阻塞
            return "4";
        }
    }

    //进入竞拍室后根据当前的最高价获取加价策略
    @RequestMapping("/tbRoom/getStepPrice")
    @ResponseBody
    public Integer getStepPrice(int rid){
        return tbRoomService.getBasicRoomItems(rid).get(0).getStepprice();
    }

    //显示logo
    @RequestMapping("/showLogo")
    @ResponseBody
    public LogoAddress showLogo(){
        LogoAddress address = new LogoAddress();
        address.setAddress(NginxPicAddress+"logo2.png");
        return address;
    }

    @RequestMapping("/cleanRubbish")
    @ResponseBody
    public void cleanRubbish(HttpServletRequest req){
        String token = super.getCookieVal(req,"token");
        SysUser user = JwtUtils.getObject(token,SysUser.class);
        List<TbProduct> tbt = tbProductService.selectByUserId(user.getId().intValue());
        tbt.stream().forEach(o->{
            if(o.getName().equals("RUBBISH") || o.getTypeid()==0){
                tbProductService.deleteRubbish(o);
            }
        });
    }

}
