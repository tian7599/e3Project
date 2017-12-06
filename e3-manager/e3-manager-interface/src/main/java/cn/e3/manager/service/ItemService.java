package cn.e3.manager.service;

import cn.e3.pojo.TbItem;
import cn.e3.utils.DatagridPageBean;

public interface ItemService {

	/**
	 * 需求: 根据id查询
	 */
	public TbItem findByTbItemId(Long id);
	
	/**
	 * 需求: 分页查询商品列表
	 * 参数:Integer page, Integer rows
	 * 返回值:DatagridPageBean
	 */
	public DatagridPageBean findItemListByPage(Integer page, Integer rows);
}
