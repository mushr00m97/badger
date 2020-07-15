package com.mushr00m.controller;

import com.mushr00m.utils.mushr00mUtils.StringFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import com.mushr00m.utils.BaseController;
import org.springframework.web.client.RestTemplate;

@Controller
public class HomeController extends BaseController {

	@Autowired
	private RestTemplate restTemplate;

	@RequestMapping("/")
	public String home() {
		String api = "http://127.0.0.1:8003/mushr00m/stammer/Poetry/fengCi";
		//restTemplate.getForEntity(api,String.class).getBody();
		return "mushr00m/home";
	}

	@RequestMapping("/openWork/{work}")
	public String openWork(@PathVariable("work") String work) {
//		badger模块
		if(work.contains("Badger")){
			if(work.contains("Upload")){
				return "redirect:/badgerProduct/goToUpload";
			}
			String res = StringFilter.removeAppointString(work,"Badger");
			return "mushr00m/badger/"+res;

		}

//		bess模块
		if(work.contains("Bess")){
			String res = StringFilter.removeAppointString(work,"Bess");
			return "mushr00m/bess/"+res;
		}
//		bingo模块
		if(work.contains("Bingo")){
			String res = StringFilter.removeAppointString(work,"Bingo");
			return "mushr00m/bingo/"+res;
		}
//		busy模块
		if(work.contains("Busy")){
			String res = StringFilter.removeAppointString(work,"Busy");
			return "mushr00m/busy/"+res;
		}
		if(work.contains("redis")){
			String res = StringFilter.removeAppointString(work,"redis");
			return "mushr00m/busy/"+res;
		}

		if(work.contains("MQ")){
			String res = StringFilter.removeAppointString(work,"MQ");
			return "mushr00m/busy/"+res;
		}

//		bitter模块
		if(work.contains("BitterWelcome")){
			String res = StringFilter.removeAppointString(work,"Bitter");
			return "mushr00m/bitter/"+res;
		}
		return "mushr00m/"+work;
	}


	@RequestMapping({"/success","/info"})
	public String success() {
		return "mushr00m/success";
	}


}