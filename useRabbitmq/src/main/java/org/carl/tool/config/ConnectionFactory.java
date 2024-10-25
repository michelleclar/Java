package org.carl.tool.config;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ShutdownSignalException;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.TimeoutException;

public class ConnectionFactory implements PooledObjectFactory<Connection> {
    com.rabbitmq.client.ConnectionFactory connectionFactory;
    static final Logger logger = LoggerFactory.getLogger(ConnectionFactory.class);

    {
        connectionFactory = new com.rabbitmq.client.ConnectionFactory();
// "guest"/"guest" by default, limited to localhost connections
        connectionFactory.setUsername("root");
        connectionFactory.setPassword("root");
        connectionFactory.setVirtualHost("/");
        connectionFactory.setHost("localhost");
        connectionFactory.setPort(15672);
    }

    @Override
    public void activateObject(PooledObject<Connection> p) {
        logger.info("activateObject:{}", p.getObject());
    }

    @Override
    public void destroyObject(PooledObject<Connection> p) throws Exception {
        p.getObject().close();
        logger.debug("connection closed");

    }

    @Override
    public PooledObject<Connection> makeObject() {
        try {
            Connection conn = connectionFactory.newConnection();
            return new DefaultPooledObject<>(conn);
        } catch (IOException | TimeoutException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void passivateObject(PooledObject<Connection> p) throws Exception {
        logger.debug("passivateObject:{}", p.getObject());
    }

    @Override
    public boolean validateObject(PooledObject<Connection> p) {
        try {
            // NOTE: get back return ,just 'idea' not throw warning
            ShutdownSignalException closeReason = p.getObject().getCloseReason();
            if (Objects.isNull(closeReason)) return true;
        } catch (ShutdownSignalException e) {
            return false;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return false;
        }
        return false;
    }
}
