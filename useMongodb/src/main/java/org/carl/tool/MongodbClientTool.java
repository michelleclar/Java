package org.carl.tool;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;
import java.util.function.Function;

import static java.util.concurrent.TimeUnit.SECONDS;

public class MongodbClientTool {
    static MongoClient mongoClient;
    static final Logger logger = LoggerFactory.getLogger(MongoClient.class);

    static {
        mongoClient = MongoClients.create(
                MongoClientSettings.builder().applyConnectionString(new ConnectionString("mongodb://localhost:37017"))
                        .applyToConnectionPoolSettings(builder ->
                                builder.maxWaitTime(10, SECONDS)
                                        .maxSize(200))
                        .build());
        logger.info("mongodb init complete");

    }

    public static MongoDatabase getDatabase(String databaseName) {
        return executor(client -> client.getDatabase(databaseName)).orElseThrow();
    }


    public static <T> Optional<T> executor(Function<MongoClient, T> function) {
        return Optional.ofNullable(function.apply(mongoClient));
    }

}
