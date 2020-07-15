package com.mushr00m.controller;

import com.huaban.analysis.jieba.JiebaSegmenter;
import com.huaban.analysis.jieba.SegToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.mushr00m.utils.BaseController;
import com.mushr00m.entity.Poetry;
import com.mushr00m.service.PoetryService;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/mushr00m/Poetry")
public class PoetryController extends BaseController {

	@Autowired
	private PoetryService poetryService;

	@Autowired
	private RestTemplate restTemplate;

	@RequestMapping("/fengCi")
	@ResponseBody
	public String fengCi(){
		String api = "http://127.0.0.1:8003/mushr00m/stammer/Poetry/fengCi";
		//String res = restTemplate.getForEntity(api,String.class).getBody();
		//return res;
		return null;
	}

	@RequestMapping("/select")
	@ResponseBody
	public List<Poetry> selectPoetryByCondWithSeg(String text){
		String api = "http://127.0.0.1:8003/mushr00m/stammer/Poetry/select?text="+text;
		//List<Poetry> list =  restTemplate.getForEntity(api, List.class).getBody();
		//return list;
		return null;

	}

	@RequestMapping("/showContent")
	@ResponseBody
	public String showContent(int id){
		String ids = Integer.toString(id);
//		String api = "http://127.0.0.1:8003/mushr00m/stammer/Poetry/showContent?id="+ids;
//		String content =  restTemplate.getForEntity(api,String.class).getBody();
//		return content;
		return null;

	}



}