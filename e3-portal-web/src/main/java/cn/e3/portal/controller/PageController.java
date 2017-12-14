package cn.e3.portal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


import cn.e3.content.service.ContentService;
import cn.e3.utils.AdItem;
import cn.e3.utils.JsonUtils;

@Controller
public class PageController {
	
	@Autowired
	private ContentService contentService;
	
	@Value("${BIG_AD_CATEGORY_ID}")
	private Long BIG_AD_CATEGORY_ID;
	
	
	/**
	 * 需求:前台页面跳转,同时查询并回显大广告
	 */
	@RequestMapping("index")
	public String showIndex(Model model){
		List<AdItem> adList =  contentService.findContentAdList(BIG_AD_CATEGORY_ID);
		//初始化大广告
		String json = JsonUtils.objectToJson(adList);
		model.addAttribute("ad1", json);
		return "index";
	}
}
