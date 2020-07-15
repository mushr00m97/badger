package com.mushr00m.service;

import com.huaban.analysis.jieba.JiebaSegmenter;
import com.mushr00m.model.SysUser;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.mushr00m.entity.TbProduct;
import com.mushr00m.dao.TbProductMapper;
import redis.clients.jedis.Jedis;

import java.util.*;
import java.util.stream.Collectors;

@Transactional
@Service
public class TbProductService {

	Jedis jedis = new Jedis("127.0.0.1",6379);

	@Autowired
	private TbProductMapper tbproductMapper;

	////查询功能区
	//查询所有商品
	public List<TbProduct> selectAllProduct(){
		return tbproductMapper.selectAll();
	}

	//按条件查询[不包含模糊搜索]
	public List<TbProduct> selectByCond(String type, String cond) {
		List<TbProduct> listSC = new ArrayList<>();
		if(!type.equals("0")) {
			listSC = tbproductMapper.selectAll().stream().filter(o -> o.getTypeid().toString().equals(type) && o.getState().toString().equals("1"))
					.collect(Collectors.toList());
			if(cond.equals("0") || cond.equals("1")){
				return listSC.stream().sorted(Comparator.comparing(TbProduct::getCreatetime)).collect(Collectors.toList());
			}
				return listSC.stream().sorted(Comparator.comparing(TbProduct::getCreatetime).reversed()).collect(Collectors.toList());
		}

		if(cond.equals("0") || cond.equals("1")){
			return tbproductMapper.selectAll().stream().sorted(Comparator.comparing(TbProduct::getCreatetime)).collect(Collectors.toList());
		}else{
			return tbproductMapper.selectAll().stream().sorted(Comparator.comparing(TbProduct::getCreatetime).reversed()).collect(Collectors.toList());
		}
	}

	//通过id查具体商品【模糊搜索的分词中使用】
	public TbProduct selectById(String idd){
		int id = Integer.parseInt(idd);
		return tbproductMapper.selectByPrimaryKey(id);
	}

	//通过id返回商品[list形式返回]
	public List<TbProduct> selectProductByPID(String pid){
		TbProduct tbProduct = new TbProduct();
		tbProduct.setId(Integer.parseInt(pid));
		return tbproductMapper.select(tbProduct);
	}

	//模糊搜索
	public List<TbProduct> selectByText(String text){
		List<TbProduct> llist = new ArrayList<>();
		JiebaSegmenter jiebaText = new JiebaSegmenter();
		jiebaText.process(text, JiebaSegmenter.SegMode.SEARCH).stream().forEach(t->{
			Set<String> ids = jedis.smembers(t.word);
			for(String id:ids){
				llist.add(tbproductMapper.selectByPrimaryKey(id));
			}
		});
		return llist.stream()
				.filter(o->o.getState().toString().equals("1"))
				.collect(Collectors.toList());
	}

	///商品管理工作区

	//添加商品前创建一个空的商品并进行主键回写
	public int createBody(SysUser user){
		TbProduct tbProduct = new TbProduct();
		tbProduct.setAccount(user.getAccount());
		tbProduct.setUserid(user.getId().intValue());
		tbProduct.setState(0);
		tbProduct.setCreatetime(new Date());
		tbProduct.setName("RUBBISH");
		tbProduct.setTypeid(0);
		tbproductMapper.insert(tbProduct);
		return tbProduct.getId();
	}

	//给一个空商品上传图片
	public int updateImg(TbProduct tt,String image){
		tt.setImg(image);
		return tbproductMapper.updateByPrimaryKey(tt);
	}

	//商品添加页面完善商品其他信息
	public int NewGoodAddItem(TbProduct tbProduct, String type, String name) {
		tbProduct.setName(name);
		tbProduct.setTypeid(Integer.parseInt(type));
		return tbproductMapper.updateByPrimaryKey(tbProduct);
	}

	public void deleteRubbish(TbProduct tbr) {

		tbproductMapper.delete(tbr);
	}

	//根据某个用户的id查出他名下所有的商品
	public List<TbProduct> selectByUserId(int uid) {

		TbProduct tb = new TbProduct();
		tb.setUserid(uid);
		return tbproductMapper.select(tb);
	}

	//根据用户id修改上下架状态
	public void updateState(TbProduct t1) {
		tbproductMapper.updateByPrimaryKey(t1);
	}


	public List<TbProduct> selectByCondSec(TbProduct crt) {
		return tbproductMapper.select(crt);
	}
}