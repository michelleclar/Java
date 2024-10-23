package org.carl.tool.action;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.UpdateRequest;
import co.elastic.clients.elasticsearch.core.UpdateResponse;
import org.carl.tool.utils.ElasticsearchClientToolUtils;

import javax.annotation.Nullable;
import java.io.IOException;
import java.util.List;

public class Update<T, K> {
    UpdateRequest.Builder<T, K> builder;
    ElasticsearchClient esClient;

    public Update(ElasticsearchClient esClient, UpdateRequest.Builder<T, K> builder) {
        this.esClient = esClient;
        this.builder = builder;
    }

    public _Query<T, K> index(String indexName) {
        this.builder.index(indexName);
        return new _Query<>(this);
    }

    public static class _Query<T, K> {
        Update<T, K> update;

        public _Query(Update<T, K> update) {
            this.update = update;
        }

        public Action<T, K> id(String value) {
            this.update.builder.id(value);
            return new Action<>(this.update);
        }
    }

    public static class Action<T, K> {
        Update<T, K> update;

        public Action(Update<T, K> update) {
            this.update = update;
        }

        public Executor<T, K> upsert(@Nullable T t) {

            this.update.builder.upsert(t);
            return new Executor<>(this.update);

        }
    }

    public static class Executor<T, K> {
        Update<T, K> update;

        public Executor(Update<T, K> update) {
            this.update = update;
        }


        public UpdateResponse<T> executor(Class<T> clazz) {
            try {
                return this.update.esClient.update(this.update.builder.build(), clazz);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
