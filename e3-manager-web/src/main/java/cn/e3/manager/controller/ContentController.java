package cn.e3.manager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.e3.content.service.ContentService;
import cn.e3.pojo.TbContent;
import cn.e3.utils.DatagridPageBean;
import cn.e3.utils.E3mallResult;

@Controller
public class ContentController {

	@Autowired
	private ContentService contentService;
	/**
	 * 需求:根据外键查询广告内容数据,分页展示
	 * 请求:/content/query/list
	 * 参数:Long categoryId,Integer page,Integer rows
	 * 返回值:DatagridPageBean (json)
	 */
	@RequestMapping("/content/query/list")
	@ResponseBody
	public DatagridPageBean findContentListByPage(Long categoryId,
			@RequestParam(defaultValue="1") Integer page,
			@RequestParam(defaultValue="10") Integer rows){
		DatagridPageBean pageBean = contentService.findContentListByPage(categoryId, page, rows);
		return pageBean;
	}
	
	/**
	 * 需求:保存广告数据
	 * 请求:/content/save
	 * 参数:TbContent content
	 * 返回值:E3mallResult
	 */
	@RequestMapping("/content/save")
	@ResponseBody
	public E3mallResult saveContent(TbContent content){
		E3mallResult result = contentService.saveContent(content);
		return result;
	}
}
