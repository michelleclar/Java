package org.carl.config;

import com.mongodb.client.MongoClient;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import java.time.Duration;

public class MongodbClientPoolConfig extends GenericObjectPoolConfig<MongoClient> {
    public MongodbClientPoolConfig() {
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

    // suggest enable
    setJmxEnabled(true);
  }
}
