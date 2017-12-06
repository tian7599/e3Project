package cn.e3.manager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.e3.manager.service.ItemService;
import cn.e3.pojo.TbItem;
import cn.e3.utils.DatagridPageBean;



@Controller
public class ItemController {
	
	@Autowired
	private ItemService itemService;

	/**
	 * 需求:根据id查询商品数据
	 * @param itemId
	 * @return
	 */
	@RequestMapping("item/list/{itemId}")
	@ResponseBody
	public TbItem findTbItemById(@PathVariable Long itemId){
		TbItem item = itemService.findByTbItemId(itemId);
		return item;
	}
	/**
	 * 需求: 分页查询商品列表
	 * 请求:/item/list
	 * 参数:Integer page, Integer rows
	 * 返回值:DatagridPageBean
	 */
	@RequestMapping("/item/list")
	@ResponseBody
	public DatagridPageBean findByPage(@RequestParam(defaultValue="1") Integer page,
			@RequestParam(defaultValue="30") Integer rows){
		DatagridPageBean pageBean = itemService.findItemListByPage(page, rows);
		return pageBean;
	}
	
}
