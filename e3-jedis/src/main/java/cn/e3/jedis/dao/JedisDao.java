package cn.e3.jedis.dao;

public interface JedisDao {
	
	//抽取jedis常用命令
	//string
	String set(String key,String value);
	
	String get(String key);
	
	Long incr(String key);
	
	Long decr(String key);
	
	//hash
	Long hset(String key,String field,String value);
	
	String hget(String key,String field);
	
	Long hdel(String key,String field);
	//设置过期时间
	Long expire(String key,int seconds);
	//查看剩余时间
	Long ttl(String key);
	
}
