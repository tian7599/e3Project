package cn.e3.manager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.e3.search.service.SearchItemService;
import cn.e3.utils.E3mallResult;

@Controller
public class SolrIndexController {

	@Autowired
	private SearchItemService searchItemService;
	/**
	 * 需求:查询索引域字段对应的数据库字段,导入索引库
	 * 请求:/index/item/import
	 * 返回值:E3mallResult
	 */
	@RequestMapping("/index/item/import")
	@ResponseBody
	public E3mallResult findDatabaseToSolrIndex(){
		E3mallResult result = searchItemService.findDatabaseToSolrIndex();
		return result;
	}
}
