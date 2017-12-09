package cn.e3.manager.service;

import java.util.List;

import cn.e3.utils.TreeNode;

public interface ItemCatService {

	/**
	 * 需求:根据父id查询子节点
	 * 参数:Long parentId
	 * 返回值: [{
	 * 			"id": 1;
	 * 			"text" : "Node1";
	 * 			"state" : "closed";
	 * 			}]
	 * ( 如果 is_parent=1,表示节点有子节点,state=closed(可打开)
	 *  如果 is_parent=0,表示节点无子节点,state=open(已经打开,无法再打开) )
	 */
	List<TreeNode> findItemCatTreeNodeList(Long parentId);
	
}
