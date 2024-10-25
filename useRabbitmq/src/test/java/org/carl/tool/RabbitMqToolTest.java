package org.carl.tool;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import org.carl.tool.config.RabbitMqPool;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class RabbitMqToolTest {

    @Test
    void createExchange() {
        System.out.println(RabbitMqTool.createExchange("e-1"));
    }

    @Test
    void createQueue() {
        System.out.println(RabbitMqTool.createQueue("q-1"));
    }

    @Test
    void testCreateQueue() {
        System.out.println(RabbitMqTool.createQueue("q-1-quorum", RabbitMqTool.QueueType.QUORUM));
    }

    @Test
    void sendMsg() {
        RabbitMqTool.sendMsg("q-1", "hi");
    }

    @Test
    void bind() {
        System.out.println(RabbitMqTool.bind("e-1", "q-1", ""));
    }

    @Test
    void getMsg() {
        System.out.println(RabbitMqTool.getMsg("q-1"));
    }

    @Test
    void listenQ() throws IOException {
        new Thread(() -> {
            RabbitMqTool.listenQ("q-1", new RabbitMqConsume() {
                @Override
                public void handleConsumeOk(String consumerTag) {
                    System.out.println("消费者启动成功");
                }

                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) {
                    System.out.println("消息内容：" + new String(body));
                }
            });
        })
                .start();
        System.in.read();
    }
}