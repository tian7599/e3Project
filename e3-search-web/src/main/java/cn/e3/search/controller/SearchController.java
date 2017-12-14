package cn.e3.search.controller;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.e3.search.pojo.SolrPage;
import cn.e3.search.service.SearchItemService;


@Controller
public class SearchController {

	@Autowired
	private SearchItemService searchItemService;
	
	/**
	 * http://localhost:8085/search.html?q=
	 */
	@RequestMapping("search")
	public String find(@RequestParam(value="q") String qName,
			@RequestParam(defaultValue="1") Integer page,
			@RequestParam(defaultValue="1") Integer rows,
			Model model){
		
		//处理乱码
		try {
			qName = new String(qName.getBytes("ISO8859-1"), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		SolrPage solrPage = searchItemService.findSolrIndex(qName, page, rows);
		
		model.addAttribute("query", qName);
		model.addAttribute("totalPages", solrPage.getTotalPages());
		model.addAttribute("page", page);
		model.addAttribute("itemList", solrPage.getItemList());
		
		return "search";
	}
	
}
