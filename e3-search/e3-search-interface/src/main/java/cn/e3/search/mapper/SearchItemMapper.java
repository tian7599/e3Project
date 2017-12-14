package cn.e3.search.mapper;

import java.util.List;

import cn.e3.search.pojo.SearchItem;

public interface SearchItemMapper {
	
	/**
	 * 查询索引域字段对应的数据库字段,导入索引库
	 * @return
	 */
	List<SearchItem> findDatabaseToSolrIndex();
}
