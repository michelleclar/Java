package org.carl.tool;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.DeleteResponse;
import co.elastic.clients.elasticsearch.indices.CreateIndexResponse;
import org.carl.model.Book;
import org.carl.tool.action.Delete;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ElasticsearchClientToolTest {

    @Test
    void createIndex() {
        CreateIndexResponse book = ElasticsearchClientTool.indices().create("book");
        System.out.println(book);
    }

    @Test
    void insertDoc() {
        Book book = new Book().setId("1").setTitle("aaa").setAuthor("adsa");
        ElasticsearchClientTool.insertDoc(book);
    }

    @Test
    void insert() {
        Book book = new Book().setId("2").setTitle("bbb").setAuthor("asdas");
        ElasticsearchClientTool.index().index("book").id(book.getId()).document(book).executor();
    }

    @Test
    void get() {
        Book book = ElasticsearchClientTool.get().index("book").id("1").fetchOf(Book.class);
        System.out.println(book);
    }

    @Test
    void update() {
        Book book = new Book().setId("1").setTitle("bbb").setAuthor("222");
        ElasticsearchClientTool.update().index("book").id("1").upsert(book);
    }

    @Test
    void delete() {
        DeleteResponse book = ElasticsearchClientTool.delete().index("book").id("1").executor();
        System.out.println(book);
    }
    @Test
    void chain() {
        List<Book> books = ElasticsearchClientTool.search().index("book").query(q -> q
                .match(t -> t
                        .field("title")
                        .query("aaa")
                )).fetchOf(Book.class);
        System.out.println(books);
    }
}