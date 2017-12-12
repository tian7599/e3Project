package cn.e3.content.service;

import java.util.List;

import cn.e3.pojo.TbContent;
import cn.e3.utils.AdItem;
import cn.e3.utils.DatagridPageBean;
import cn.e3.utils.E3mallResult;

public interface ContentService {

	/**
	 * 需求:根据外键查询广告内容数据,分页展示
	 * 参数:Long categoryId
	 * 返回值:DatagridPageBean
	 */
	DatagridPageBean findContentListByPage(Long categoryId,Integer page,Integer rows);
	
	/**
	 * 需求:保存广告数据
	 * 参数:TbContent content
	 * 返回值:E3mallResult
	 */
	E3mallResult saveContent(TbContent content);

	/**
	 * 需求:查询广告
	 * 参数:AdItem adItem
	 * 返回值:Model
	 */
	List<AdItem> findContentAdList(Long bIG_AD_CATEGORY_ID);
	
	
	
}
