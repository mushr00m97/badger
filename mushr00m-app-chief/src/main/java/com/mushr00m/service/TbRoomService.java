package com.mushr00m.service;

import com.mushr00m.dao.TbProductMapper;
import com.mushr00m.entity.TbProduct;
import com.mushr00m.model.RoomDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.mushr00m.entity.TbRoom;
import com.mushr00m.dao.TbRoomMapper;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
public class TbRoomService {
	@Autowired
	private TbRoomMapper tbroomMapper;

	@Autowired
	private TbProductMapper tbProductMapper;

	////查询功能区

	//通过商品id查竞拍室[上线的]
    public List<TbRoom> selectByPID(int id) {
		return tbroomMapper.selectAll().stream().filter(o->o.getProductid()==id && o.getState() == 1)
				.collect(Collectors.toList());
    }

    //查询商品对应的竞拍时
    public List<TbRoom> selectAll(){
    	return tbroomMapper.selectAll();
	}

	//通过id查看指定的竞拍室
	public List<TbRoom> getBasicRoomItems(int id){
    	TbRoom t = new TbRoom();
    	t.setId(id);
    	return tbroomMapper.select(t);
	}

	//通过id查看指定竞拍室内的商品
	public List<TbProduct> getBasicRoomProductItems(int id){
		TbRoom t = new TbRoom();
		t.setId(id);
		TbProduct tp = new TbProduct();
		tp.setId(tbroomMapper.select(t).get(0).getProductid());
		return tbProductMapper.select(tp);
	}

	//修改竞拍室内的最高价
    public int updateMaxPrice(Double price,int id) {
    	TbRoom tbRoom = tbroomMapper.selectByPrimaryKey(id);
    	tbRoom.setMaxprice(price);
    	return tbroomMapper.updateByPrimaryKey(tbRoom);
    }

    //查看当前登录用户名下的所有竞拍室
    public List<RoomDetail> selectByUserId(int userid, int state) {
    	return tbroomMapper.selectByUserId(userid,state);
    }


    //创建新的竞拍室
    public Integer makeRoom(int pid,int start, Date dateL, int step) {
    	TbRoom tbRoom = new TbRoom();
    	tbRoom.setProductid(pid);
    	tbRoom.setStartprice(start);
    	tbRoom.setEndtime(dateL);
    	tbRoom.setCreatetime(new Date());
    	tbRoom.setStepprice(step);
    	tbRoom.setState(1);
    	tbRoom.setMaxprice((double) start);
    	tbroomMapper.insert(tbRoom);
    	return tbRoom.getId();
    }

    //创建竞拍室以后通过roomid获取刚才创建的room对象并将它加入到延时消费队列中
	public TbRoom selectByID(int rid) {
//    	TbRoom tbRoom = new TbRoom();
//    	tbRoom.setId(rid);
    	return tbroomMapper.selectByPrimaryKey(rid);
	}

	//将竞拍室下架
	public void updateStateOffline(int rid) {
    	TbRoom tbRoom = tbroomMapper.selectByPrimaryKey(rid);
    	tbRoom.setState(0);
    	tbroomMapper.updateByPrimaryKey(tbRoom);
	}
}