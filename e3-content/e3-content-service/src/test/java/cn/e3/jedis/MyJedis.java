package cn.e3.jedis;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class MyJedis {

	/**
	 * 需求:连接单机redis
	 */
	@Test
	public void linkSingleRedisWithoutPool(){
		Jedis jedis = new Jedis("192.168.62.156", 6379);
		jedis.set("username", "tom");
	}
	/**
	 * 需求:连接池连接redis
	 */
	@Test
	public void linkSingleRedisWithPool(){
		JedisPoolConfig jpconfig = new JedisPoolConfig();
		jpconfig.setMaxTotal(100);
		jpconfig.setMaxIdle(20);
		JedisPool jpool = new JedisPool(jpconfig, "192.168.62.156", 6379);
		Jedis jedis =jpool.getResource();
		jedis.set("username", "tom");
	}
	/**
	 * 需求:连接池连接集群redis
	 */
	@Test
	public void linkClusterRedisWithPool(){
		JedisPoolConfig jpconfig = new JedisPoolConfig();
		jpconfig.setMaxTotal(100);
		jpconfig.setMaxIdle(20);
		
		Set<HostAndPort> nodes = new HashSet<>();
		nodes.add(new HostAndPort("192.168.62.156", 7001));
		nodes.add(new HostAndPort("192.168.62.156", 7002));
		nodes.add(new HostAndPort("192.168.62.156", 7003));
		nodes.add(new HostAndPort("192.168.62.156", 7004));
		nodes.add(new HostAndPort("192.168.62.156", 7005));
		nodes.add(new HostAndPort("192.168.62.156", 7006));
		nodes.add(new HostAndPort("192.168.62.156", 7007));
		nodes.add(new HostAndPort("192.168.62.156", 7008));
		JedisCluster jc = new JedisCluster(nodes, jpconfig);
		jc.set("username", "tom002");
	}
	
	@Test
	public void linkClusterRedisWithSpring(){
		JedisPoolConfig jpconfig = new JedisPoolConfig();
		jpconfig.setMaxTotal(100);
		jpconfig.setMaxIdle(20);
		
		Set<HostAndPort> nodes = new HashSet<>();
		nodes.add(new HostAndPort("192.168.62.156", 7001));
		nodes.add(new HostAndPort("192.168.62.156", 7002));
		nodes.add(new HostAndPort("192.168.62.156", 7003));
		nodes.add(new HostAndPort("192.168.62.156", 7004));
		nodes.add(new HostAndPort("192.168.62.156", 7005));
		nodes.add(new HostAndPort("192.168.62.156", 7006));
		nodes.add(new HostAndPort("192.168.62.156", 7007));
		nodes.add(new HostAndPort("192.168.62.156", 7008));
		JedisCluster jc = new JedisCluster(nodes, jpconfig);
		jc.set("username", "tom002");
	}
	
}
