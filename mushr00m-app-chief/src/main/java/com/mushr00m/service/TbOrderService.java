package com.mushr00m.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.mushr00m.entity.TbOrder;
import com.mushr00m.dao.TbOrderMapper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Transactional
@Service
public class TbOrderService {
	@Autowired
	private TbOrderMapper tborderMapper;

	public TbOrderMapper getTbOrderMapper() {
		return tborderMapper;
	}

	//竞价室内添加竞价记录
	public int recordPriceList(int rid,int uid,String account,Double price){
		TbOrder tbOrder = new TbOrder();
		tbOrder.setRoomid(rid);
		tbOrder.setUserid(uid);
		tbOrder.setAccount(account);
		tbOrder.setPrice(price);
		tbOrder.setCreatetime(new Date());
		return tborderMapper.insert(tbOrder);
	}

	//竞拍结束后查询竞拍室对应的买家id
    public int selectUserId(int roomId, Double price) {
		TbOrder tbOrder = new TbOrder();
		tbOrder.setRoomid(roomId);
		tbOrder.setPrice(price);
		return tborderMapper.select(tbOrder).get(0).getUserid();
    }

    //竞拍结束后查询竞拍室对应的买家名字
	public String selectUserAccount(int roomId, Double price) {
		TbOrder tbOrder = new TbOrder();
		tbOrder.setRoomid(roomId);
		tbOrder.setPrice(price);
		return tborderMapper.select(tbOrder).get(0).getAccount();
	}

	//根据竞拍室id查询该竞拍室内的所有出价记录
	public List<TbOrder> selectOrdersByRID(int rid) {
		TbOrder tbOrder = new TbOrder();
		tbOrder.setRoomid(rid);
		return tborderMapper.select(tbOrder);
	}
}