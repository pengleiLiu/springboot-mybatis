package com.lpl.springboot1.config;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisNode;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import redis.clients.jedis.JedisPoolConfig;

@Configuration
//@EnableAutoConfiguration
@EnableCaching
@PropertySource("classpath:config/redis.properties")
public class RedisConfig extends CachingConfigurerSupport {

	@Value("${redis.maxIdle}")
	private Integer maxIdle;
	
	@Value("${redis.maxTotal}")
	private Integer maxTotal;
	
	@Value("${redis.maxWaitMillis}")
	private Integer maxWaitMillis;
	
	@Value("${redis.minEvictableIdleTimeMillis}")
	private Integer minEvictableIdleTimeMillis;
	
	@Value("${redis.numTestsPerEvictionRun}")
	private Integer numTestsPerEvictionRun;
	
	@Value("${redis.timeBetweenEvictionRunsMillis}")
	private long timeBetweenEvictionRunsMillis;
	
	@Value("${redis.testOnBorrow}")
	private boolean testOnBorrow;
	
	@Value("${redis.testWhileIdle}")
	private boolean testWhileIdle;
	
		
	@Value("${spring.redis.cluster.nodes}")
	private String clusterNodes; 
	
	@Value("${spring.redis.cluster.max-redirects}")
	private Integer mmaxRedirectsac;
		
	/**
	 * JedisPoolConfig 连接池
	 * @return
	 */
	@Bean
	public JedisPoolConfig jedisPoolConfig() {
		JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
		// 最大空闲数
		jedisPoolConfig.setMaxIdle(maxIdle);
		// 连接池的最大数据库连接数
		jedisPoolConfig.setMaxTotal(maxTotal);
		// 最大建立连接等待时间
		jedisPoolConfig.setMaxWaitMillis(maxWaitMillis);
		// 逐出连接的最小空闲时间 默认1800000毫秒(30分钟)
		jedisPoolConfig.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
		// 每次逐出检查时 逐出的最大数目 如果为负数就是 : 1/abs(n), 默认3
		jedisPoolConfig.setNumTestsPerEvictionRun(numTestsPerEvictionRun);
		// 逐出扫描的时间间隔(毫秒) 如果为负数,则不运行逐出线程, 默认-1
		jedisPoolConfig.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
		// 是否在从池中取出连接前进行检验,如果检验失败,则从池中去除连接并尝试取出另一个
		jedisPoolConfig.setTestOnBorrow(testOnBorrow);
		// 在空闲时检查有效性, 默认false
		jedisPoolConfig.setTestWhileIdle(testWhileIdle);
		return jedisPoolConfig;
	}
	
	/**
	 * Redis集群的配置
	* @return RedisClusterConfiguration
	* @autor lpl
	* @date 2017年12月22日
	* @throws
	 */
	@Bean
	public RedisClusterConfiguration redisClusterConfiguration(){
		RedisClusterConfiguration redisClusterConfiguration = new RedisClusterConfiguration();
		//Set<RedisNode> clusterNodes
		String[] serverArray = clusterNodes.split(",");
		
		Set<RedisNode> nodes = new HashSet<RedisNode>();
		
		for(String ipPort:serverArray){
			String[] ipAndPort = ipPort.split(":");
			nodes.add(new RedisNode(ipAndPort[0].trim(),Integer.valueOf(ipAndPort[1])));
		}
		
		redisClusterConfiguration.setClusterNodes(nodes);
		redisClusterConfiguration.setMaxRedirects(mmaxRedirectsac);
		
		return redisClusterConfiguration;
	}
	/**
	 * 
	* @Title: JedisConnectionFactory 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param jedisPoolConfig
	* @param @return
	* @return JedisConnectionFactory
	* @autor lpl
	* @date 2017年12月22日
	* @throws
	 */
	@Bean
	public JedisConnectionFactory JedisConnectionFactory(JedisPoolConfig jedisPoolConfig,RedisClusterConfiguration redisClusterConfiguration){
		JedisConnectionFactory JedisConnectionFactory = new JedisConnectionFactory(redisClusterConfiguration,jedisPoolConfig);
		return JedisConnectionFactory; 
	}
	/**
	 * 实例化 RedisTemplate 对象
	 *
	 * @return
	 */
	@Bean(name="redisTemplate")
	public RedisTemplate<String, Object> functionDomainRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
		RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
		initDomainRedisTemplate(redisTemplate, redisConnectionFactory);
		return redisTemplate;
	}
	/**
	 * 设置数据存入 redis 的序列化方式,并开启事务
	 * 
	 * @param redisTemplate
	 * @param factory
	 */
	private void initDomainRedisTemplate(RedisTemplate<String, Object> redisTemplate, RedisConnectionFactory factory) {
		//如果不配置Serializer，那么存储的时候缺省使用String，如果用User类型存储，那么会提示错误User can't cast to String！  
		redisTemplate.setKeySerializer(new StringRedisSerializer());
		redisTemplate.setHashKeySerializer(new StringRedisSerializer());
		redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());
		redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
		// 开启事务
		redisTemplate.setEnableTransactionSupport(true);
		redisTemplate.setConnectionFactory(factory);
	}
	
	/**
     * 设置RedisCacheManager
     * 使用cache注解管理redis缓存
     *
     * @return
     */
    @Bean
    public RedisCacheManager cacheManager(@SuppressWarnings("rawtypes")RedisTemplate redisTemplate) {
        RedisCacheManager redisCacheManager = new RedisCacheManager(redisTemplate);
        return redisCacheManager;
    }
    
    @Bean
    public KeyGenerator keyGenerator() {
        return new KeyGenerator() {
			@Override
			public Object generate(Object o, Method method, Object... objects) {
				 StringBuilder sb = new StringBuilder();
	                sb.append(o.getClass().getName()).append(".");
	                sb.append(method.getName()).append(".");
	                for (Object obj : objects) {
	                    sb.append(obj.toString());
	                }
	                System.out.println("keyGenerator=" + sb.toString());
	                return sb.toString();
			}
        };
    }
    
}
