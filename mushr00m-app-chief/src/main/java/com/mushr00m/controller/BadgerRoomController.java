package com.mushr00m.controller;


import com.mushr00m.entity.TbProduct;
import com.mushr00m.model.RoomDetail;
import com.mushr00m.model.SysUser;
import com.mushr00m.service.TbProductService;
import com.mushr00m.service.TbRoomService;
import com.mushr00m.utils.BaseController;
import com.mushr00m.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/badgerRoom")
public class BadgerRoomController extends BaseController {

    @Value("${Nginx.UploadAddress}")
    private String uploadDir;

    @Value("${Nginx.address}")
    private String NginxPicAddress;

    @Autowired
    private TbProductService tbProductService;

    @Autowired
    private TbRoomService tbRoomService;

    ///竞拍室 创建模块

    @RequestMapping("/selectRoomDetailByCond")
    @ResponseBody
    public List<RoomDetail> selectRoomDetailByCond(
                   int cond, HttpServletRequest req){
        List<RoomDetail> resultList =  new ArrayList<>();

        String token = super.getCookieVal(req,"token");
        SysUser user = JwtUtils.getObject(token,SysUser.class);
        int userid = user.getId().intValue();
        List<RoomDetail> list = tbRoomService.selectByUserId(userid,cond);
        list.stream().forEach(o->{
            o.setImg(NginxPicAddress+o.getImg());
        });
        return list;
    }

    //从竞拍室管理跳转到竞拍室添加
    @RequestMapping("/goToAddRoom")
    public String goToAddRoom(){
        return "mushr00m/badger/addroom";
    }

    ///竞拍室创建模块

    //查询该用户名下所有上线的商品
    @RequestMapping("/selectProductsByUID")
    @ResponseBody
    public List<TbProduct> selectProductsByUID(HttpServletRequest req){
        String token = super.getCookieVal(req,"token");
        SysUser user = JwtUtils.getObject(token,SysUser.class);
        List<TbProduct> listC = tbProductService.selectByUserId(user.getId().intValue())
                .stream().filter(o->o.getState()==1).collect(Collectors.toList());

        listC.stream().forEach(o->{o.setImg(NginxPicAddress+o.getImg());});
        return listC;
    }

    //点开竞拍室创建按钮
    @RequestMapping("/GoToCreateRoom")
    public String GoToCreateRoom(Model model, int pid){
        model.addAttribute("pid",pid);
        return "mushr00m/badger/createroom";
    }

    //创建竞拍室
    @RequestMapping("/MakeRoom")
    @ResponseBody
    public Integer MakeRoom(int pid,int start,String date,int step) throws ParseException {
        Date dateL = new SimpleDateFormat("yyyy-MM-dd hh:mm").parse(date);
            return tbRoomService.makeRoom(pid,start,dateL,step);

    }

    //从用户上架的竞拍室列表跳转到不包含出价功能的竞拍室页面
    @RequestMapping("/OwnertoTbRoom")
    public String OwnertoTbRoom(){
        return "mushr00m/badger/tbroomOwner";
    }
}
