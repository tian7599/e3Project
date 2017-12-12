package cn.e3.content.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.e3.content.service.ContentService;
import cn.e3.mapper.TbContentMapper;
import cn.e3.pojo.TbContent;
import cn.e3.pojo.TbContentExample;
import cn.e3.pojo.TbContentExample.Criteria;
import cn.e3.utils.AdItem;
import cn.e3.utils.DatagridPageBean;
import cn.e3.utils.E3mallResult;
@Service
public class ContentServiceImpl implements ContentService {

	@Autowired
	private TbContentMapper tbContentMapper;
	@Value("${WIDTH}")
	private Integer WIDTH;
	@Value("${WIDTHB}")
	private Integer WIDTHB;
	@Value("${HEIGHTB}")
	private Integer HEIGHTB;
	@Value("${HEIGHT}")
	private Integer HEIGHT;
	
	/**
	 * 需求:根据外键查询广告内容数据,分页展示
	 * 参数:Long categoryId,Integer page,Integer rows
	 * 返回值:DatagridPageBean
	 */
	@Override
	public DatagridPageBean findContentListByPage(Long categoryId,Integer page,Integer rows) {
		
		TbContentExample example = new TbContentExample();
		Criteria criteria = example.createCriteria();
		criteria.andCategoryIdEqualTo(categoryId);
		
		PageHelper.startPage(page, rows);
		List<TbContent> contentList = tbContentMapper.selectByExample(example);
		PageInfo<TbContent> pageInfo = new PageInfo<>(contentList);
		
		DatagridPageBean pageBean = new DatagridPageBean();
		pageBean.setRows(contentList);
		pageBean.setTotal(pageInfo.getTotal());
		
		return pageBean;
	}
	
	/**
	 * 需求:保存广告数据
	 * 参数:TbContent content
	 * 返回值:E3mallResult
	 */
	@Override
	public E3mallResult saveContent(TbContent content) {
		Date date = new Date();
		content.setCreated(date);
		content.setUpdated(date);
		tbContentMapper.insertSelective(content);
		return E3mallResult.ok();
	}

	@Override
	public List<AdItem> findContentAdList(Long bIG_AD_CATEGORY_ID) {
		// TODO Auto-generated method stub
		TbContentExample example = new TbContentExample();
		example.createCriteria().andCategoryIdEqualTo(bIG_AD_CATEGORY_ID);
		List<TbContent> list = tbContentMapper.selectByExample(example);
		
		List<AdItem> adList = new ArrayList<>();
		for (TbContent tbContent : list) {
			AdItem adItem = new AdItem();
			
			adItem.setAlt(tbContent.getSubTitle());
			adItem.setHref(tbContent.getUrl());
			adItem.setSrc(tbContent.getPic());
			adItem.setSrcB(tbContent.getPic2());
			
			adItem.setHeight(HEIGHT);
			adItem.setHeightB(HEIGHTB);
			adItem.setWidth(WIDTH);
			adItem.setWidthB(WIDTHB);
			
			adList.add(adItem);
		}
		return adList;
	}

}
