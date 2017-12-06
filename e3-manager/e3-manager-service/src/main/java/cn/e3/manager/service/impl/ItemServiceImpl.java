package cn.e3.manager.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.e3.manager.service.ItemService;
import cn.e3.mapper.TbItemMapper;
import cn.e3.pojo.TbItem;
import cn.e3.pojo.TbItemExample;
import cn.e3.utils.DatagridPageBean;
@Service
public class ItemServiceImpl implements ItemService{

	@Autowired
	private TbItemMapper tbItemMapper;
	
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
	

}
