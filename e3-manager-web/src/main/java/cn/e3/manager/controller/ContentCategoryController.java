package cn.e3.manager.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.e3.content.service.ContentCatagoryService;
import cn.e3.utils.E3mallResult;
import cn.e3.utils.TreeNode;

@Controller
public class ContentCategoryController {

	@Autowired
	private ContentCatagoryService contentCatagoryService;
	/**
	 * 需求:根据父节点id查询其子节点
	 * 请求:/content/category/list
	 * 参数:Long ParentId
	 * 返回值 : List<TreeNode>
	 * 业务: value="id",defaultValue="0"(默认第一次加载)
	 */
	@RequestMapping("/content/category/list")
	@ResponseBody
	public List<TreeNode> findContentCategoryList(@RequestParam(value="id",defaultValue="0") Long parentId){
		List<TreeNode> list = contentCatagoryService.findContentCatagoryTreeNodeList(parentId);
		return list;
	}
	
	/**
	 * 需求:新建树形节点
	 * 请求:/content/category/create
	 * 参数:Long parentId,String name
	 * 返回值: E3mallResult
	 */
	@RequestMapping("/content/category/create")
	@ResponseBody
	public E3mallResult createNode(Long parentId,String name){
		E3mallResult result = contentCatagoryService.createNode(parentId, name);
		return result;
	}
}
