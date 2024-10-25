package org.carl.tool;

import com.rabbitmq.client.*;

import java.io.IOException;

public class RabbitMqConsume implements Consumer {
    /**
     * Channel that this consumer is associated with.
     */
    private Channel _channel;
    /**
     * Consumer tag for this consumer.
     */
    private volatile String _consumerTag;

    public RabbitMqConsume() {

    }

    protected void setChannel(Channel channel) {
        this._channel = channel;
    }

    /**
     * Stores the most recently passed-in consumerTag - semantically, there should be only one.
     *
     * @see Consumer#handleConsumeOk
     */
    @Override
    public void handleConsumeOk(String consumerTag) {
        this._consumerTag = consumerTag;
    }

    /**
     * No-op implementation of {@link Consumer#handleCancelOk}.
     *
     * @param consumerTag the defined consumer tag (client- or server-generated)
     */
    @Override
    public void handleCancelOk(String consumerTag) {
        // no work to do
    }

    /**
     * No-op implementation of {@link Consumer#handleCancel(String)}
     *
     * @param consumerTag the defined consumer tag (client- or server-generated)
     */
    @Override
    public void handleCancel(String consumerTag) throws IOException {
        // no work to do
    }

    /**
     * No-op implementation of {@link Consumer#handleShutdownSignal}.
     */
    @Override
    public void handleShutdownSignal(String consumerTag, ShutdownSignalException sig) {
        // no work to do
    }

    /**
     * No-op implementation of {@link Consumer#handleRecoverOk}.
     */
    @Override
    public void handleRecoverOk(String consumerTag) {
        // no work to do
    }

    /**
     * No-op implementation of {@link Consumer#handleDelivery}.
     */
    @Override
    public void handleDelivery(String consumerTag,
                               Envelope envelope,
                               AMQP.BasicProperties properties,
                               byte[] body)
            throws IOException {
        // no work to do
    }

    /**
     * Retrieve the channel.
     *
     * @return the channel this consumer is attached to.
     */
    public Channel getChannel() {
        return _channel;
    }

    /**
     * Retrieve the consumer tag.
     *
     * @return the most recently notified consumer tag.
     */
    public String getConsumerTag() {
        return _consumerTag;
    }
}
