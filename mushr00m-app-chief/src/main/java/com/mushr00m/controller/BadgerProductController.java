package com.mushr00m.controller;

import com.mushr00m.entity.TbProduct;
import com.mushr00m.model.LogoAddress;
import com.mushr00m.model.SysUser;
import com.mushr00m.service.TbProductService;
import com.mushr00m.utils.BaseController;
import com.mushr00m.utils.JwtUtils;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/badgerProduct")
public class BadgerProductController extends BaseController {

    @Value("${Nginx.UploadAddress}")
    private String uploadDir;

    @Autowired
    private TbProductService tbProductService;

    @Value("${Nginx.address}")
    private String NginxPicAddress;

    ///商品 创建模块

   //商品图片上传
    @RequestMapping("/upload")
    @ResponseBody
    public String upload(MultipartFile file,@RequestParam("pid")int pid) throws IOException {
        //将图片上传至nginx并获取图片名
        String fileN = UUID.randomUUID()+file.getOriginalFilename();
        String dir = uploadDir+fileN;
        file.transferTo(new File(dir));

        //将图片信息添加到空的商品对象内
        TbProduct tbProduct = tbProductService.selectById(pid+"");
        int result = tbProductService.updateImg(tbProduct,fileN);

        if(result == 0){
            System.out.println("上传失败");
        }

        return "上传成功";
    }

    //跳转到upload页面并携带创建的商品对象id
    @RequestMapping("/goToUpload")
    public String goToUpload(Model model,HttpServletRequest req){

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

        //刚进入页面时创建一个空的product对象并进行主键回写
        int pid = tbProductService.createBody(user);
        model.addAttribute("pid",pid);
        return "/mushr00m/badger/upload";
    }

    //按照商品id获取商品图片
    @RequestMapping("/showPic")
    @ResponseBody
    public String showPic(int pid){
        return NginxPicAddress+tbProductService.selectById(pid+"").getImg();

    }

    @RequestMapping("/NewGoodAddItem")
    @ResponseBody
    public int NewGoodAddItem(String type,String name,int pid,HttpServletRequest req){

        //完善商品数据
        TbProduct tbProduct = tbProductService.selectById(pid+"");
        int result = tbProductService.NewGoodAddItem(tbProduct,type,name);

        return result;
    }


    ///商品管理模块

    //查询出当前用户名下的所有商品
    @RequestMapping("/selectAllProductByUserId")
    @ResponseBody
    public List<TbProduct> selectAllProductByUserId(HttpServletRequest req){
        String token = super.getCookieVal(req,"token");
        SysUser user = JwtUtils.getObject(token,SysUser.class);

        List<TbProduct> list=  tbProductService.selectByUserId(user.getId().intValue()).stream().
                filter(o->o.getTypeid()!=0).collect(Collectors.toList());
        list.stream().forEach(o->{o.setImg(NginxPicAddress+o.getImg());});
        return list;
    }

    //跳转到商品上下架页面
    @RequestMapping("/goToSetState")
    public String setState(Model model,int pid){
        model.addAttribute("pid",pid);
        return "mushr00m/badger/setState";
    }

    //修改选中的商品上下架状态
    @RequestMapping("/changeState")
    @ResponseBody
    public void changeState(int pid){
        TbProduct t1 = tbProductService.selectProductByPID(pid+"").get(0);
        if(t1.getState()==0){
            t1.setState(1);
        }else{
            t1.setState(0);
        }
        tbProductService.updateState(t1);
    }

    //上下架修改栏中获取状态
    @RequestMapping("/selectProductByPID")
    @ResponseBody
    public TbProduct selectProductByPID(int pid){
        TbProduct product =  tbProductService.selectProductByPID(pid+"").get(0);
         product.setImg(NginxPicAddress+product.getImg());
         return product;
    }

    //货架的分类查询
    @RequestMapping("/selectByCond")
    @ResponseBody
    public List<TbProduct> selectByCond(int type,int cond,HttpServletRequest req){
        String token = super.getCookieVal(req,"token");
        SysUser user = JwtUtils.getObject(token, SysUser.class);

       TbProduct crt = new TbProduct();
        crt.setUserid(user.getId().intValue());

        if(cond == 1){
            crt.setState(1);
        }else if (cond == 2) {
            crt.setState(0);
        }

        if(type!=0) {
            crt.setTypeid(type);
        }

        List<TbProduct> list =  tbProductService.selectByCondSec(crt);
        list.stream().forEach(o->{o.setImg(NginxPicAddress+o.getImg());});
        return list;
    }

    //显示logo
    @RequestMapping("/showLogo")
    @ResponseBody
    public LogoAddress showLogo(){
        LogoAddress address = new LogoAddress();
        address.setAddress(NginxPicAddress+"logo2.png");
        return address;
    }


}
