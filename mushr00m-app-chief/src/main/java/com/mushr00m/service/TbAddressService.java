package com.mushr00m.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.mushr00m.entity.TbAddress;
import com.mushr00m.dao.TbAddressMapper;

import java.util.List;

@Transactional
@Service
public class TbAddressService {
	@Autowired
	private TbAddressMapper tbaddressMapper;

	public TbAddressMapper getTbAddressMapper() {
		return tbaddressMapper;
	}

	//根据用户id查询他的所有地址
    public List<TbAddress> selectAddressByUID(int intValue) {
		TbAddress tbAddress = new TbAddress();
		tbAddress.setUid(intValue);
		return tbaddressMapper.select(tbAddress);
    }

    //添加用户地址
	public int makeAddress(String address, int intValue) {
		TbAddress tbAddress = new TbAddress();
		tbAddress.setUid(intValue);
		tbAddress.setAddress(address);
		return tbaddressMapper.insert(tbAddress);
	}

	//根据id删除地址
	public void deleteAddress(int id) {
		TbAddress tbAddress = new TbAddress();
		tbAddress.setId(id);
		tbaddressMapper.delete(tbAddress);
	}

	//通过id获取address
    public TbAddress getAddressByID(int aid) {
		return tbaddressMapper.selectByPrimaryKey(aid);
    }

}