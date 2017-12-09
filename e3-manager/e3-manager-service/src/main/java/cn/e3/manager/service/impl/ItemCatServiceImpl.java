package cn.e3.manager.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;

import cn.e3.manager.service.ItemCatService;
import cn.e3.mapper.TbItemCatMapper;
import cn.e3.pojo.TbItemCat;
import cn.e3.pojo.TbItemCatExample;
import cn.e3.pojo.TbItemCatExample.Criteria;
import cn.e3.utils.TreeNode;
@Service
public class ItemCatServiceImpl implements ItemCatService {
	
	@Autowired
	private TbItemCatMapper tbItemCatMapper;
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
	 *  
	 *  服务发布
	 */
	@Override
	public List<TreeNode> findItemCatTreeNodeList(Long parentId) {
		
		TbItemCatExample example = new TbItemCatExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		List<TbItemCat> list = tbItemCatMapper.selectByExample(example);
		List<TreeNode> treeList = new ArrayList<>();
		for (TbItemCat tbItemCat : list) {
			TreeNode node = new TreeNode();
			node.setId(tbItemCat.getId());
			node.setText(tbItemCat.getName());
			node.setState(tbItemCat.getIsParent()?"closed":"open");
			treeList.add(node);
		}
		return treeList;
	}

}
