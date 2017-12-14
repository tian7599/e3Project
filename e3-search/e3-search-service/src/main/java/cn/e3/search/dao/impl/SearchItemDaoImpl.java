package cn.e3.search.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.e3.search.dao.SearchItemDao;
import cn.e3.search.pojo.SearchItem;
import cn.e3.search.pojo.SolrPage;
@Repository
public class SearchItemDaoImpl implements SearchItemDao {

	@Autowired
	private SolrServer solrServer;
	
	@Override
	public SolrPage findSolrIndex(SolrQuery solrQuery) {
		SolrPage pagebean = new SolrPage();
		ArrayList<SearchItem> slist = new ArrayList<>();
		try {
			QueryResponse response = solrServer.query(solrQuery);
			SolrDocumentList results = response.getResults();
			//命中总记录数
			Long found = results.getNumFound();
			pagebean.setTotalCount(found.intValue());
			
			for (SolrDocument sDoc : results) {
				SearchItem item = new SearchItem(); 
				
				String id = (String) sDoc.get("id");
				item.setId(Long.parseLong(id));
				
				//标题
				String item_title = (String)sDoc.get("item_title");
				//高亮
				Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();
				//第一个map的key是id
				Map<String, List<String>> map = highlighting.get(id);
				//第二个map的key是高亮字段
				List<String> list = map.get("item_title");
				if(list!=null && list.size()>0){
					item_title = list.get(0);
				}
				item.setTitle(item_title);
				
				
				item.setSell_point((String)sDoc.get("item_sell_point"));
				item.setPrice((Long)sDoc.get("item_price"));
				item.setImage((String)sDoc.get("item_image"));
				item.setCategory_name((String)sDoc.get("item_category_name"));
				
				slist.add(item);
			}
			
			pagebean.setItemList(slist);
			
		} catch (SolrServerException e) {
			e.printStackTrace();
		}
		
		return pagebean;
	}

}
