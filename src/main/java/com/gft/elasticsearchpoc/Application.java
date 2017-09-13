package com.gft.elasticsearchpoc;

import com.gft.elasticsearchpoc.model.Book;
import com.gft.elasticsearchpoc.service.BookService;
import org.elasticsearch.client.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Created by eusvercher on 5/9/17.
 */
@SpringBootApplication
public class Application implements CommandLineRunner {

    private final Logger logger = LoggerFactory.getLogger(Application.class);

    @Autowired
    private ElasticsearchOperations es;

    @Autowired
    private BookService bookService;

    public static void main(String args[]) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        printElasticSearchInfo();

        bookService.save(new Book("1001", "Elasticsearch Basics", "Rambabu Posa", "23-FEB-2017"));
        bookService.save(new Book("1002", "Apache Lucene Basics", "Rambabu Posa", "13-MAR-2017"));
        bookService.save(new Book("1003", "Apache Solr Basics", "Rambabu Posa", "21-MAR-2017"));
        bookService.save(new Book("1004", "Apache Solr Basics_1", "Rambabu Posa_1", "21-MAR-2017"));
        bookService.save(new Book("1005", "Apache Solr Basics_1", "Rambabu Posa_1", "21-MAR-2017"));


        logger.warn("WARN");
        logger.warn("WARN");

        logger.error("ERROR");

        //fuzzey search
        Page<Book> books = bookService.findByAuthor("Rambabu", new PageRequest(0, 10));

        //List<Book> books = bookService.findByTitle("Elasticsearch Basics");

        books.forEach(x -> System.out.println(x));


    }

    //useful for debug, print elastic search details
    private void printElasticSearchInfo() {

        System.out.println("--ElasticSearch--");
        Client client = es.getClient();
        Map<String, String> asMap = client.settings().getAsMap();

        asMap.forEach((k, v) -> {
            System.out.println(k + " = " + v);
        });
        System.out.println("--ElasticSearch--");
    }
}
