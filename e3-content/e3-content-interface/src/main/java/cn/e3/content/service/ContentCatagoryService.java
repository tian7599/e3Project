package cn.e3.content.service;

import java.util.List;

import cn.e3.utils.E3mallResult;
import cn.e3.utils.TreeNode;

public interface ContentCatagoryService {

	/**
	 * 需求:根据父节点id查询其子节点
	 * 参数:Long ParentId
	 * 返回值 : List<TreeNode>
	 */
	List<TreeNode> findContentCatagoryTreeNodeList(Long parentId);
	
	/**
	 * 需求:新建树形节点
	 * 参数:Long parentId,String name
	 * 返回值: E3mallResult
	 */
	E3mallResult createNode(Long parentId,String name);
}
