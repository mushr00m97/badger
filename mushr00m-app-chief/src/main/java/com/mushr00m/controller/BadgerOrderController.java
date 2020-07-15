package com.mushr00m.controller;

import com.mushr00m.entity.TbAddress;
import com.mushr00m.entity.TbFinalorder;
import com.mushr00m.entity.TbOrder;
import com.mushr00m.model.SysUser;
import com.mushr00m.service.TbAddressService;
import com.mushr00m.service.TbFinalorderService;
import com.mushr00m.service.TbOrderService;
import com.mushr00m.utils.BaseController;
import com.mushr00m.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/order")
public class BadgerOrderController extends BaseController {

    @Autowired
    private TbFinalorderService tbFinalorderService;

    @Autowired
    private TbAddressService tbAddressService;

    @Autowired
    private TbOrderService tbOrderService;

    ///我的订单模块

    //显示我的订单
    @RequestMapping("/selectFinalOrderByUID")
    @ResponseBody
    public List<TbFinalorder> selectFinalOrderByUID(HttpServletRequest req){
        String token = super.getCookieVal(req,"token");
        SysUser sysUser = JwtUtils.getObject(token,SysUser.class);
        return tbFinalorderService.selectFinalOrderByUID(sysUser.getId().intValue());
    }

    //按条件查询我的订单
    @RequestMapping("/selectFinalOrdersByCond")
    @ResponseBody
    public List<TbFinalorder> selectFinalOrdersByCond(String cond,HttpServletRequest req){
        String token = super.getCookieVal(req,"token");
        SysUser sysUser = JwtUtils.getObject(token,SysUser.class);
        List<TbFinalorder> list = tbFinalorderService.selectFinalOrderByUID(sysUser.getId().intValue());
        List<TbFinalorder> list1 = new ArrayList<>();
        if(cond.equals("0")){

             list1 = list.stream().filter(o->o.getState()==0).collect(Collectors.toList());
        }else if(cond.equals("1")){
            list1 = list.stream().filter(o->o.getState()==1).collect(Collectors.toList());
        }else{
            list1 = list.stream().filter(o->o.getState()==2).collect(Collectors.toList());
        }
        return list1;
    }

    //去支付【添加地址】
    @RequestMapping("/toAddAddress")
    public String toAddAddress(Model model,int fid){
        model.addAttribute("fid",fid);
        return "mushr00m/badger/finalOrderAddAddress";
    }

    //确认收货地址
    @RequestMapping("/confirmAddress")
    @ResponseBody
    public int confirmAddress(int fid,String address){
        return tbFinalorderService.addAddress(fid,address);

    }

    ///我的出售模块

    //按条件查询我的出售
    @RequestMapping("/selectFinalOrdersByCondForOwner")
    @ResponseBody
    public List<TbFinalorder> selectFinalOrdersByCondForOwner(String cond,HttpServletRequest req){
        String token = super.getCookieVal(req,"token");
        SysUser sysUser = JwtUtils.getObject(token,SysUser.class);
        List<TbFinalorder> listC = tbFinalorderService.selectFinalOrderByUIDForOwner(sysUser.getId().intValue());
        List<TbFinalorder> listF = new ArrayList<>();
        if(cond.equals("0")){
            listF = listC.stream().filter(o->o.getState()==0).collect(Collectors.toList());
        }else if(cond.equals("1")){
            listF = listC.stream().filter(o->o.getState()==1).collect(Collectors.toList());
        }else{
            listF = listC.stream().filter(o->o.getState()==2).collect(Collectors.toList());
        }
        return listF;
    }

    //查看订单收货地址
    @RequestMapping("/toLookAddress")
    public String toLookAddress(Model model,int fid){
        model.addAttribute("ffid",fid);
        return "mushr00m/badger/toLookAddress";
    }

    //通过地址id查看地址
    @RequestMapping("/showAddressForOwner")
    @ResponseBody
    public TbAddress showAddressForOwner(int fid){

        int addressId = tbFinalorderService.getAddressID(fid);
        return tbAddressService.getAddressByID(addressId);
    }

    //完成订单
    @RequestMapping("/finishOrder")
    @ResponseBody
    public int finishOrder(int fid){
        return tbFinalorderService.finishOrder(fid);
    }

    //根据竞拍室id查询该竞拍室内的所有出价记录
    @RequestMapping("/selectOrders")
    @ResponseBody
    public List<TbOrder> selectOrders(int rid){
        return tbOrderService.selectOrdersByRID(rid);
    }

}
