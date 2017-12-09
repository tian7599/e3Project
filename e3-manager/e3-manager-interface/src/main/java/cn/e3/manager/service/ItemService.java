package cn.e3.manager.service;

import cn.e3.pojo.TbItem;
import cn.e3.pojo.TbItemDesc;
import cn.e3.utils.DatagridPageBean;
import cn.e3.utils.E3mallResult;

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
	
	
	/**
	 * 需求:保存商品对象
	 * 参数:TbItem item, TbItemDesc itemDesc, TbItemParam itemParam
	 * 返回值 :E3mallResult
	 */
	public E3mallResult saveItem(TbItem item, TbItemDesc itemDesc);
}
