package cn.e3.content.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.e3.content.service.ContentCatagoryService;
import cn.e3.mapper.TbContentCategoryMapper;
import cn.e3.pojo.TbContentCategory;
import cn.e3.pojo.TbContentCategoryExample;
import cn.e3.pojo.TbContentCategoryExample.Criteria;
import cn.e3.utils.E3mallResult;
import cn.e3.utils.TreeNode;

@Service
public class ContentCatagoryServiceImpl implements ContentCatagoryService {

	@Autowired
	private TbContentCategoryMapper tbContentCategoryMapper;
	
	/**
	 * 需求:根据父节点id查询其子节点
	 * 参数:Long ParentId
	 * 返回值 : List<TreeNode>
	 */
	@Override
	public List<TreeNode> findContentCatagoryTreeNodeList(Long parentId) {
		TbContentCategoryExample example = new TbContentCategoryExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		List<TbContentCategory> list = tbContentCategoryMapper.selectByExample(example);
		List<TreeNode> nodeList = new ArrayList<>();
		for (TbContentCategory tbContentCategory : list) {
			TreeNode node = new TreeNode();
			node.setId(tbContentCategory.getId());
			node.setText(tbContentCategory.getName());
			node.setState(tbContentCategory.getIsParent()?"closed":"open");
			nodeList.add(node);
		}
		return nodeList;
	}

	
	/**
	 * 需求:新建树形节点
	 * 参数:Long parentId,String name
	 * 返回值: E3mallResult
	 */
	@Override
	public E3mallResult createNode(Long parentId, String name) {
		TbContentCategory tbContentCategory = new TbContentCategory();
		tbContentCategory.setParentId(parentId);
		tbContentCategory.setName(name);
		tbContentCategory.setStatus(1);
		tbContentCategory.setSortOrder(1);
		tbContentCategory.setIsParent(false);
		Date date = new Date();
		tbContentCategory.setUpdated(date);
		tbContentCategory.setCreated(date);
		TbContentCategory parentContentCategory = tbContentCategoryMapper.selectByPrimaryKey(parentId);
		if(!parentContentCategory.getIsParent()){
			parentContentCategory.setIsParent(true);
			tbContentCategoryMapper.updateByPrimaryKeySelective(parentContentCategory);
		}
		tbContentCategoryMapper.insert(tbContentCategory);
		return E3mallResult.ok(tbContentCategory);
	}

}
