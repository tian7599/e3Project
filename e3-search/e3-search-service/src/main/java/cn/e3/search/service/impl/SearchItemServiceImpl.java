package cn.e3.search.service.impl;

import java.util.List;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.e3.search.dao.SearchItemDao;
import cn.e3.search.mapper.SearchItemMapper;
import cn.e3.search.pojo.SearchItem;
import cn.e3.search.pojo.SolrPage;
import cn.e3.search.service.SearchItemService;
import cn.e3.utils.E3mallResult;
@Service
public class SearchItemServiceImpl implements SearchItemService {

	@Autowired
	private SolrServer solrServer;
	@Autowired
	private SearchItemDao searchItemDao;
	
	@Autowired
	private SearchItemMapper searchItemMapper;
	
	/**
	 * 查询索引域字段对应的数据库字段,导入索引库
	 * @return
	 */
	@Override
	public E3mallResult findDatabaseToSolrIndex() {
		try {
			List<SearchItem> list = searchItemMapper.findDatabaseToSolrIndex();
			for (SearchItem searchItem : list) {
				SolrInputDocument doc = new SolrInputDocument();
				
				doc.addField("id", searchItem.getId());
				doc.addField("item_title", searchItem.getTitle());
				doc.addField("item_sell_point", searchItem.getSell_point());
				doc.addField("item_price", searchItem.getPrice());
				doc.addField("item_image", searchItem.getImage());
				doc.addField("item_category_name", searchItem.getCategory_name());
				
				solrServer.add(doc);
			}
			solrServer.commit();
		}  catch (Exception e) {
			e.printStackTrace();
		}
		return E3mallResult.ok();
	}

	
	/**
	 * 接收参数,封装参数,查询索引库
	 * 参数 : String qName,Integer page,Integer rows
	 * 返回值 : SolrPage
	 */
	@Override
	public SolrPage findSolrIndex(String qName, Integer page, Integer rows) {
		SolrQuery solrQuery = new SolrQuery(); 
		//主查询条件
		if(qName!=null && !"".equals(qName)){
			solrQuery.setQuery(qName);
		}else{
			solrQuery.setQuery("*:*");
		}
		
		//分页
		int startNo = (page-1)*rows;
		solrQuery.setStart(startNo);
		solrQuery.setRows(rows);
		
		//高亮
		solrQuery.setHighlight(true);
		solrQuery.addHighlightField("item_title");
		solrQuery.setHighlightSimplePre("<font color='red'>");
		solrQuery.setHighlightSimplePost("</font>");
		
		//复制域
		solrQuery.set("df", "item_keywords");
		
		SolrPage solrPage = searchItemDao.findSolrIndex(solrQuery);
		
		//当前页
		solrPage.setPage(page);
		//总记录数
		Integer totalCount = solrPage.getTotalCount();
		//总页数
		int totalPages = totalCount/rows;
		if(totalCount%rows>0){
			totalPages++;
		}
		solrPage.setTotalPages(totalPages);
		
		return solrPage;
	}

}
