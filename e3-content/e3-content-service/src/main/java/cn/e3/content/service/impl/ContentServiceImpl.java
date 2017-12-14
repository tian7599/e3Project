package cn.e3.content.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.e3.content.service.ContentService;
import cn.e3.jedis.dao.JedisDao;
import cn.e3.mapper.TbContentMapper;
import cn.e3.pojo.TbContent;
import cn.e3.pojo.TbContentExample;
import cn.e3.pojo.TbContentExample.Criteria;
import cn.e3.utils.AdItem;
import cn.e3.utils.DatagridPageBean;
import cn.e3.utils.E3mallResult;
import cn.e3.utils.JsonUtils;
@Service
public class ContentServiceImpl implements ContentService {

	@Autowired
	private TbContentMapper tbContentMapper;
	@Autowired
	private JedisDao jedisDao;
	
	@Value("${WIDTH}")
	private Integer WIDTH;
	@Value("${WIDTHB}")
	private Integer WIDTHB;
	@Value("${HEIGHTB}")
	private Integer HEIGHTB;
	@Value("${HEIGHT}")
	private Integer HEIGHT;
	@Value("${INDEX_CACHE}")
	private String INDEX_CACHE;
	
	/**
	 * 需求:根据外键查询广告内容数据,分页展示(后台)
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
	 * 缓存同步:先删除缓存
	 */
	@Override
	public E3mallResult saveContent(TbContent content) {
		//删除缓存
		jedisDao.hdel(INDEX_CACHE, content.getCategoryId()+"");
		
		Date date = new Date();
		content.setCreated(date);
		content.setUpdated(date);
		tbContentMapper.insertSelective(content);
		return E3mallResult.ok();
	}

	/**
	 * 需求:查询并回显大广告(门户),设置缓存
	 * 缓存数据结构:hash
	 * key : INDEX_CACHE(首页缓存) FOOD_CACHE(食品缓存)
	 * field : categoryId(缓存分类[大广告,小广告...])
	 * value : json(缓存数据)
	 */
	@Override
	public List<AdItem> findContentAdList(Long bIG_AD_CATEGORY_ID) {
		//查询缓存
		String hget = jedisDao.hget(INDEX_CACHE, bIG_AD_CATEGORY_ID+"");
		if(StringUtils.isNotBlank(hget)){
			List<AdItem> list = JsonUtils.jsonToList(hget, AdItem.class);
			return list;
		}
		
		
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
		jedisDao.hset(INDEX_CACHE, bIG_AD_CATEGORY_ID+"", JsonUtils.objectToJson(adList));
		return adList;
	}

}
