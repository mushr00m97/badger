package com.mushr00m.controller;

import com.mushr00m.model.SysUser;
import com.mushr00m.service.DelayService;
import com.mushr00m.service.TbOrderService;
import com.mushr00m.service.TbRoomService;
import com.mushr00m.utils.BaseController;
import com.mushr00m.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


@Controller
@RequestMapping("/delay")
public class BadgerDelayController extends BaseController {

    @Autowired
    private DelayService delayService;

    @Autowired
    private TbRoomService tbRoomService;

    @Autowired
    private TbOrderService tbOrderService;


    @RequestMapping("/makeRoom")
    @ResponseBody
    public Integer MakeRoom(int pid, Double start, String date, int step, HttpServletRequest req) throws ParseException {
        Date dateL = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(date);
        Date nowTime = new Date();
        Long myTTL = dateL.getTime()-nowTime.getTime();

        if(myTTL<=0){
            return 0;
        }else {

            int rid = tbRoomService.makeRoom(pid, start.intValue(), dateL, step);

            //创建竞拍室时默认创建第一条出价信息[出价人为竞拍室发起者，出价为起拍价]
            String token = super.getCookieVal(req, "token");
            SysUser sysUser = JwtUtils.getObject(token, SysUser.class);

            tbOrderService.recordPriceList(rid, sysUser.getId().intValue(),
                    sysUser.getAccount(), start);

            delayService.send(rid, myTTL);

            return rid;
        }
    }
}
