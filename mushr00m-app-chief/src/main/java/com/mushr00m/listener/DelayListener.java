package com.mushr00m.listener;

import com.mushr00m.entity.TbFinalorder;
import com.mushr00m.entity.TbOrder;
import com.mushr00m.entity.TbProduct;
import com.mushr00m.entity.TbRoom;
import com.mushr00m.model.SysUser;
import com.mushr00m.service.TbFinalorderService;
import com.mushr00m.service.TbOrderService;
import com.mushr00m.service.TbProductService;
import com.mushr00m.service.TbRoomService;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;


@Component
public class DelayListener {

    @Autowired
    private TbRoomService tbRoomService;

    @Autowired
    private TbOrderService tbOrderService;

    @Autowired
    private TbProductService tbProductService;

    @Autowired
    private TbFinalorderService tbFinalorderService;

    @RabbitListener(queues = "repeatTradeQueue")
    public void process(int rid) {
        TbRoom tbRoom = tbRoomService.selectByID(rid);
        System.out.println(tbRoom.getMaxprice()+"listener工作了========");
        //将竞拍室下架
        tbRoomService.updateStateOffline(rid);
        //获取结束的竞拍室的相关信息
        Double price = tbRoom.getMaxprice();
        int roomId = tbRoom.getId();
        int buyerId = tbOrderService.selectUserId(roomId,price);
        String account = tbOrderService.selectUserAccount(roomId,price);

        int productId = tbRoom.getProductid();
        TbProduct tbProduct = tbProductService.selectById(productId+"");
        String pname = tbProduct.getName();
        String pimg = tbProduct.getImg();

        int ownerId = tbProduct.getUserid();

        TbFinalorder tbFinalorder = new TbFinalorder();
        tbFinalorder.setAccount(account);
        tbFinalorder.setUid(buyerId);
        tbFinalorder.setPid(productId);
        tbFinalorder.setPrice(price);
        tbFinalorder.setCreatetime(new Date());
        //0为未支付订单
        tbFinalorder.setState(0);
        tbFinalorder.setPname(pname);
        tbFinalorder.setPimg(pimg);
        tbFinalorder.setOwnerid(ownerId);
        tbFinalorder.setAddress(0);

        tbFinalorderService.addOrder(tbFinalorder);
    }

}
