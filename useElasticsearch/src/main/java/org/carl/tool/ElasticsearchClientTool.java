package org.carl.tool;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.*;
import co.elastic.clients.elasticsearch.indices.ElasticsearchIndicesClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import org.apache.http.HttpHost;
import org.carl.model.Book;
import org.carl.tool.action.*;
import org.elasticsearch.client.RestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class ElasticsearchClientTool {
    static ElasticsearchClient esClient;
    static final Logger logger = LoggerFactory.getLogger(ElasticsearchClientTool.class);

    static {
        // URL and API key if,ssl,modify http -> https
        String serverUrl = "http://localhost:19200";
//        String apiKey = "VnVhQ2ZHY0JDZGJrU...";

//         Create the low-level client
        RestClient restClient = RestClient
                .builder(HttpHost.create(serverUrl))
//                .setDefaultHeaders(new Header[]{
//                        new BasicHeader("Authorization", "ApiKey " + apiKey)
//                })
                .build();

        // Create the transport with a Jackson mapper
        ElasticsearchTransport transport = new RestClientTransport(
                restClient, new JacksonJsonpMapper());

        // And create the API client
        esClient = new ElasticsearchClient(transport);

        // Use the client...

        // Close the transport, freeing the underlying thread
//        try {
//            transport.close();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
    }

    public static void createIndex(String indexName) {
        try {
            esClient.indices().create(c -> c.index(indexName));
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    public static void insertDoc(Book book) {
        try {
            IndexResponse response = esClient.index(i -> i.index("book").id(book.getId()).document(book));
            logger.info("Indexed with version {}", response.version());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> Index<T> index() {
        IndexRequest.Builder<T> builder = new IndexRequest.Builder<>();
        return new Index<>(esClient, builder);
    }

    public static Delete delete() {
        DeleteRequest.Builder builder = new DeleteRequest.Builder();
        return new Delete(esClient, builder);
    }

    public static Get get() {
        GetRequest.Builder builder = new GetRequest.Builder();
        return new Get(esClient, builder);
    }

    public static Search search() {
        SearchRequest.Builder builder = new SearchRequest.Builder();
        return new Search(esClient, builder);
    }

    // NOTE: T need update doc, K need read es doc
    public static <T, K> Update<T, K> update() {
        UpdateRequest.Builder<T, K> builder = new UpdateRequest.Builder<>();
        return new Update<>(esClient, builder);
    }


    public static Indices indices() {
        ElasticsearchIndicesClient indices = esClient.indices();
        return new Indices(indices);
    }

}
