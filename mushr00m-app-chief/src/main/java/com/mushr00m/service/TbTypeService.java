package com.mushr00m.service;

import com.mushr00m.entity.TbProduct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.mushr00m.entity.TbType;
import com.mushr00m.dao.TbTypeMapper;

import java.util.List;

@Transactional
@Service
public class TbTypeService {
	@Autowired
	private TbTypeMapper tbtypeMapper;

	////查询功能区

	//查询所有类型
	public List<TbType> selectAllType(){
		return tbtypeMapper.selectAll();
	}

	//获取商品类型名[倒排索引时提供数据]
    public TbType selectTypeNameById(int id){
		return tbtypeMapper.selectByPrimaryKey(id);
	}
}