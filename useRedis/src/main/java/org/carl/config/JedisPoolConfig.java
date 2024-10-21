package org.carl.config;

import java.time.Duration;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import redis.clients.jedis.Jedis;

public class JedisPoolConfig extends GenericObjectPoolConfig<Jedis> {
  public JedisPoolConfig() {
    // default is 8
    setMaxTotal(8);
    setMaxIdle(8);

    // default is true
    setBlockWhenExhausted(true);
    setMaxWait(Duration.ofSeconds(20));

    // cache Frequent use set false
    setTestOnBorrow(false);
    setTestOnReturn(false);

    setMinEvictableIdleDuration(Duration.ofSeconds(60));
    setTimeBetweenEvictionRuns(Duration.ofSeconds(30));
    setNumTestsPerEvictionRun(-1);
    setTestWhileIdle(true);
    setTestWhileIdle(true);

    // suggest enable and redis need
    setJmxEnabled(true);
  }
}
