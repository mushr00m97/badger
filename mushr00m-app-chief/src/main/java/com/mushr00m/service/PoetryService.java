package com.mushr00m.service;

import com.huaban.analysis.jieba.SegToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.mushr00m.entity.Poetry;
import com.mushr00m.dao.PoetryMapper;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Transactional
@Service
public class PoetryService {
	@Autowired
	private PoetryMapper poetryMapper;

	Jedis jedis = new Jedis("127.0.0.1",6379);

	public PoetryMapper getPoetryMapper() {
		return poetryMapper;
	}

	public List<Poetry> selectAll(){
		return poetryMapper.selectAll();
	}

	public List<Poetry> selectByText(List<SegToken> textTokenList) {
		List<Poetry> poetryList = new ArrayList<>();
		for (SegToken s : textTokenList) {

			Set<String> IDset = jedis.smembers(s.word);


			for (String s2 : IDset) {
				Poetry p = poetryMapper.selectByPrimaryKey(s2);
				poetryList.add(p);
			}
		}
		return poetryList;
	}

	public String selectById(int id){
		return poetryMapper.selectByPrimaryKey(id).getContent();
	}

}