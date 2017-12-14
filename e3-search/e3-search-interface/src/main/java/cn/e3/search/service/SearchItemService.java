package cn.e3.search.service;


import cn.e3.search.pojo.SolrPage;
import cn.e3.utils.E3mallResult;

public interface SearchItemService {

	/**
	 * 查询索引域字段对应的数据库字段,导入索引库
	 */
	E3mallResult findDatabaseToSolrIndex();
	
	/**
	 * 接收参数,封装参数,查询索引库
	 * 参数 : String qName,Integer page,Integer rows
	 * 返回值 : SolrPage
	 */
	SolrPage findSolrIndex(String qName,Integer page,Integer rows);
}
