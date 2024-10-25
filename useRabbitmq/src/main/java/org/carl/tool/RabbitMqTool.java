package org.carl.tool;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.GetResponse;
import org.carl.tool.config.RabbitMqPool;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class RabbitMqTool {
    static RabbitMqPool rabbitMqPool;

    static {
        rabbitMqPool = new RabbitMqPool();
    }

    public static AMQP.Exchange.DeclareOk createExchange(String exchangeName) {
        return rabbitMqPool.get(channel -> {
            try {
                return channel.exchangeDeclare(exchangeName, "direct", true);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public static AMQP.Queue.DeclareOk createQueue(String queueName) {
        return rabbitMqPool.get(channel -> new Option(channel, null, queueName, null).build().createQ());
    }

    public static AMQP.Queue.DeclareOk createQueue(String queueName, QueueType queueType) {
        return rabbitMqPool.get(channel -> new Option(channel, queueType, queueName, null).build().createQ());
    }

    public static void sendMsg(String queueName, String msg) {
        rabbitMqPool.run(channel -> {
            new Option(channel, null, queueName, null)
                    .setChannel(channel).build().sendMsg(msg);
        });
    }

    public static AMQP.Queue.BindOk bind(String exchangeName, String queueName, String routingKey) {
        return rabbitMqPool.get(channel -> {
            try {
                return channel.queueBind(queueName, exchangeName, routingKey);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public static GetResponse getMsg(String queueName) {
        return rabbitMqPool.get(channel -> {
            try {
                return channel.basicGet(queueName, true);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public static List<GetResponse> getAllMsg(String queueName) {
        GetResponse msg = getMsg(queueName);
        List<GetResponse> r = new ArrayList<>();
        while (msg != null) {
            r.add(msg);
            msg = getMsg(queueName);
        }
        return r;
    }

    public static void listenQ(String queueName, RabbitMqConsume consumer) {
        rabbitMqPool.run(channel -> {
            try {
                consumer.setChannel(channel);
                channel.basicConsume(queueName, consumer);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public static class Option {
        Channel channel;
        QueueType queueType;
        String queueName;
        String exchangeName;

        public Option() {
        }

        public Option(Channel channel, QueueType queueType, String queueName, String exchangeName) {
            this.channel = channel;
            this.queueType = queueType;
            this.queueName = queueName;
            this.exchangeName = exchangeName;
        }

        public Execute build() {
            if (this.exchangeName == null) this.exchangeName = "";
            return new Execute(this);
        }

        public Channel getChannel() {
            return channel;
        }

        public Option setChannel(Channel channel) {
            this.channel = channel;
            return this;
        }

        public QueueType getQueueType() {
            return queueType;
        }

        public Option setQueueType(QueueType queueType) {
            this.queueType = queueType;
            return this;
        }

        public String getQueueName() {
            return queueName;
        }

        public Option setQueueName(String queueName) {
            this.queueName = queueName;
            return this;
        }

        public String getExchangeName() {
            return exchangeName;
        }

        public Option setExchangeName(String exchangeName) {
            this.exchangeName = exchangeName;
            return this;
        }

        public static class Execute {
            Option option;

            public Execute(Option option) {
                this.option = option;
            }

            public void sendMsg(String msg) {
                try {
                    this.option.channel.basicPublish(this.option.exchangeName, this.option.queueName, null, (msg).getBytes());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            public AMQP.Queue.DeclareOk createQ() {
                try {
                    if (this.option.queueType == null)
                        return this.option.channel.queueDeclare(this.option.queueName, true, false, false, null);
                    switch (this.option.queueType) {
                        case QUORUM -> {
                            return createQ(argsMap -> argsMap.put("x-queue-type", "quorum"));
                        }
                        case STREAM -> {
                            return createQ(argsMap -> argsMap.put("x-queue-type", "stream"));
                        }
                        default -> {
                            return this.option.channel.queueDeclare(this.option.queueName, true, false, false, null);
                        }
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            public AMQP.Queue.DeclareOk createQ(Consumer<Map<String, Object>> fn) {
                try {

                    Map<String, Object> argMap = new HashMap<>();
                    fn.accept(argMap);
                    return this.option.channel.queueDeclare(this.option.queueName, true, false, false, argMap);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }


    }

    public enum QueueType {
        QUORUM,
        STREAM;
    }


}
