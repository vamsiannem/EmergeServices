package com.lister.esb.redis;

import redis.clients.jedis.Jedis;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import java.util.Set;
/**
 * Created with IntelliJ IDEA.
 * User: rajeev
 * Date: 5/4/13
 * Time: 11:26 AM
 * To change this template use File | Settings | File Templates.
 */
public class JedisTest {

    private static Logger logger = LoggerFactory.getLogger(JedisTest.class);

   // @Scheduled(fixedRate = 5000)
    public void testJedis(){
        Jedis jedis = new Jedis("localhost");
        jedis.sadd("hi", "a");
        jedis.sadd("hi", "b");

        jedis.sadd("bar", "b");
        jedis.sadd("bar", "c");

        Set<String> intersection = jedis.sinter("hi", "bar");


        logger.info(" "+intersection+" ");
    }
}
