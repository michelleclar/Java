package org.carl.tool.config;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import java.time.Duration;
import java.util.function.Consumer;
import java.util.function.Function;

public class RabbitMqPool {
    GenericObjectPool<Connection> pool;

    public RabbitMqPool() {
        GenericObjectPoolConfig<Connection> connectionGenericObjectPoolConfig = new GenericObjectPoolConfig<>();
        config(connectionGenericObjectPoolConfig);
        ConnectionFactory connectionFactory = new ConnectionFactory();
        pool = new GenericObjectPool<>(connectionFactory, connectionGenericObjectPoolConfig);
    }

    public <T> T get(Function<Channel, T> fn) {
        Connection connection = null;
        try {
            connection = pool.borrowObject();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        try (Channel channel = connection.createChannel()) {
            return fn.apply(channel);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            pool.returnObject(connection);
        }
    }

    public void run(Consumer<Channel> fn) {
        Connection connection = null;
        try {
            connection = pool.borrowObject();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        try (Channel channel = connection.createChannel()) {
            fn.accept(channel);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            pool.returnObject(connection);
        }
    }

    void config(GenericObjectPoolConfig<Connection> config) {
        // default is 8
        config.setMaxTotal(8);
        config.setMaxIdle(8);

        // default is true
        config.setBlockWhenExhausted(true);
        config.setMaxWait(Duration.ofSeconds(20));

        // cache Frequent use set false
        config.setTestOnBorrow(false);
        config.setTestOnReturn(false);

        config.setMinEvictableIdleDuration(Duration.ofSeconds(60));
        config.setTimeBetweenEvictionRuns(Duration.ofSeconds(30));
        config.setNumTestsPerEvictionRun(-1);
        config.setTestWhileIdle(true);
        config.setTestWhileIdle(true);

        // suggest enable
        config.setJmxEnabled(true);

    }
}
