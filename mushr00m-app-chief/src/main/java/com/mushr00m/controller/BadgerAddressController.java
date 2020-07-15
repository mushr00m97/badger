package com.mushr00m.controller;

import com.mushr00m.entity.TbAddress;
import com.mushr00m.model.SysUser;
import com.mushr00m.service.TbAddressService;
import com.mushr00m.utils.BaseController;
import com.mushr00m.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/address")
public class BadgerAddressController extends BaseController {

    @Autowired
    private TbAddressService tbAddressService;


    ///用户地址模块[丐中丐版]

    //从”我的买入“跳转到地址管理页面
    @RequestMapping("/goToAddressView")
    public String goToAddress(){
        return "mushr00m/badger/address";
    }

    //跳转到地址新建页面
    @RequestMapping("/goToCreateAddress")
    public String goToCreateAddress(){

        return "mushr00m/badger/createAddress";
    }

    //查询当前用户的所有地址
    @RequestMapping("/showAddress")
    @ResponseBody
    public List<TbAddress> showAddress(HttpServletRequest req){
        String token = super.getCookieVal(req,"token");
        SysUser user = JwtUtils.getObject(token,SysUser.class);
        return tbAddressService.selectAddressByUID(user.getId().intValue());

    }

    //添加收货地址
    @RequestMapping("/makeAddress")
    @ResponseBody
    public int makeAddress(String address1,String address2,
       String address3,String address4,HttpServletRequest req){
        String token = super.getCookieVal(req,"token");
        SysUser user = JwtUtils.getObject(token,SysUser.class);
        String address = address1+address2+address3+address4;
        return tbAddressService.makeAddress(address,user.getId().intValue());
    }

    //删除地址
    @RequestMapping("/deleteAddress")
    @ResponseBody
    public String deleteAddress(int id){
        tbAddressService.deleteAddress(id);
        return "ok";
    }

}
