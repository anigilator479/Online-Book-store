package com.example.onlinebookstore;

import com.example.onlinebookstore.model.Book;
import com.example.onlinebookstore.service.BookService;
import java.math.BigDecimal;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class OnlineBookStoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(OnlineBookStoreApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(BookService bookService) {
        return args -> {
            Book book = new Book();
            book.setTitle("Bobr adventure 2");
            book.setDescription("Bayayayo, bayayayo");
            book.setPrice(BigDecimal.valueOf(999));
            book.setAuthor("Ne mowie po polskie");
            book.setCoverImage("Bobr");
            book.setDescription("very cool bobr");
            book.setIsbn("88005553535");

            Book save = bookService.save(book);
            System.out.println(save);

            System.out.println(bookService.findAll());
        };
    }
}
