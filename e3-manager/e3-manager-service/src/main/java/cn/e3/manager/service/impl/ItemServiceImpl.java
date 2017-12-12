package cn.e3.manager.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.e3.manager.service.ItemService;
import cn.e3.mapper.TbItemDescMapper;
import cn.e3.mapper.TbItemMapper;
import cn.e3.mapper.TbItemParamItemMapper;
import cn.e3.pojo.TbItem;
import cn.e3.pojo.TbItemDesc;
import cn.e3.pojo.TbItemExample;
import cn.e3.pojo.TbItemParam;
import cn.e3.pojo.TbItemParamItem;
import cn.e3.utils.DatagridPageBean;
import cn.e3.utils.E3mallResult;
import cn.e3.utils.IDUtils;
@Service
public class ItemServiceImpl implements ItemService{

	@Autowired
	private TbItemMapper tbItemMapper;
	@Autowired
	private TbItemDescMapper tbItemDescMapper;
	@Autowired
	private TbItemParamItemMapper tbItemParamItemMapper;
	
	public TbItem findByTbItemId(Long id) {
		return tbItemMapper.selectByPrimaryKey(id);
	}

	/**
	 * 需求: 分页查询商品列表
	 * 参数:Integer page, Integer rows
	 * 返回值:DatagridPageBean
	 * 服务发布
	 */
	@Override
	public DatagridPageBean findItemListByPage(Integer page, Integer rows) {
		TbItemExample example = new TbItemExample();
		PageHelper.startPage(page, rows);
		List<TbItem> list = tbItemMapper.selectByExample(example);
		
		PageInfo<TbItem> pageInfo = new PageInfo<TbItem>(list);
		
		DatagridPageBean pageBean = new DatagridPageBean();
		pageBean.setRows(list);
		pageBean.setTotal(pageInfo.getTotal());
		
		return pageBean;
	}

	
	/**
	 * 需求:保存商品对象
	 * 参数:TbItem item, TbItemDesc itemDesc, String itemParams
	 * 返回值 :E3mallResult
	 */
	@Override
	public E3mallResult saveItem(TbItem item, TbItemDesc itemDesc, String itemParams) {
		//生成商品ID,(毫秒+随机数)
		long itemId = IDUtils.genItemId();
		item.setId(itemId);
		item.setStatus((byte)1);
		Date date = new Date();
		item.setCreated(date);
		item.setUpdated(date);
		
		tbItemMapper.insert(item);
		
		itemDesc.setItemId(itemId);
		itemDesc.setCreated(date);
		itemDesc.setUpdated(date);
		
		tbItemDescMapper.insert(itemDesc);
		
		TbItemParamItem tbItemParamItem = new TbItemParamItem();
		tbItemParamItem.setItemId(itemId);
		tbItemParamItem.setParamData(itemParams);
		tbItemParamItem.setCreated(date);
		tbItemParamItem.setUpdated(date);
		
		tbItemParamItemMapper.insertSelective(tbItemParamItem);
		
		return E3mallResult.ok();
	}
	

}
