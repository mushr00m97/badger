package com.mushr00m.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.mushr00m.entity.TbFinalorder;
import com.mushr00m.dao.TbFinalorderMapper;

import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
public class TbFinalorderService {
	@Autowired
	private TbFinalorderMapper tbfinalorderMapper;

	@Value("${Nginx.address}")
	private String NginxPicAddress;

	public TbFinalorderMapper getTbFinalorderMapper() {
		return tbfinalorderMapper;
	}

	public void addOrder(TbFinalorder tbFinalorder) {
		tbfinalorderMapper.insert(tbFinalorder);
	}

	//显示我的订单
	public List<TbFinalorder> selectFinalOrderByUID(int uid) {
		TbFinalorder tbFinalorder = new TbFinalorder();
		tbFinalorder.setUid(uid);
		List<TbFinalorder> list = tbfinalorderMapper.select(tbFinalorder);
		list.forEach(o->{o.setPimg(NginxPicAddress+o.getPimg());});
		return list;
	}

	//通过id给订单加入地址
    public int addAddress(int fid, String address) {
		TbFinalorder tbFinalorder = tbfinalorderMapper.selectByPrimaryKey(fid);
		tbFinalorder.setAddress(Integer.parseInt(address));
		tbFinalorder.setState(1);
		return tbfinalorderMapper.updateByPrimaryKey(tbFinalorder);
    }

    //显示我的出售订单
	public List<TbFinalorder> selectFinalOrderByUIDForOwner(int ownerId){
		TbFinalorder tbFinalorder = new TbFinalorder();
		tbFinalorder.setOwnerid(ownerId);
		List<TbFinalorder> list = tbfinalorderMapper.select(tbFinalorder);
		list.forEach(o->{o.setPimg(NginxPicAddress+o.getPimg());});
		return list;
	}

	//通过订单id获取地址id
	public int getAddressID(int ffid) {
		return tbfinalorderMapper.selectByPrimaryKey(ffid).getAddress();
	}

	//将订单状态改为已完成
	public int finishOrder(int fid) {
		TbFinalorder tbFinalorder = tbfinalorderMapper.selectByPrimaryKey(fid);
		tbFinalorder.setState(2);
		return tbfinalorderMapper.updateByPrimaryKey(tbFinalorder);
	}
}