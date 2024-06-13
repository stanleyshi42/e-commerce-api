package com.example.e_commerce_api.controller;

import com.example.e_commerce_api.entity.Book;
import com.example.e_commerce_api.service.BookService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
public class BookController {

    @Autowired
    BookService service;

    private static final Logger logger = LoggerFactory.getLogger(LoggingController.class);


    @GetMapping("/books")
    public List<Book> getAllBooks() {
        logger.info("All books are retrieved.");
        return service.getAllBooks();
    }

    @GetMapping("/books/sorted")
    public List<Book> getAllBooksByTitleOrder() {
        logger.info("All books are retrieved by title order.");

        return service.getAllBooksByTitleOrder();
    }

    @GetMapping("/books/id/{id}")
    public ResponseEntity<?> getBookById(@PathVariable long id) {


        Book result = service.getBookById(id);
        if (result == null) {
            logger.warn("Book is not found");
            return ResponseEntity.status(404).body("Book not found");
        }
        logger.info("A book is  retrieved by Id.");
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/books/title/{title}")
    public Book getBookByTitle(@PathVariable String title) {
        logger.info("A book is retrieved by title");


        return service.getBookByTitle(title);
    }

    @PostMapping("/books")
    public Book addBook(@RequestBody Book book) {


        // Prevent overwriting a book with same ID
        book.setId(0);

        // Check if book with given title already exists
        Book result = service.getBookByTitle(book.getTitle());


        if (result != null) {
            logger.warn("Book already exists");
            return null;
        }
        logger.info("Book is added to the storage.");
        return service.addBook(book);
    }

    @PutMapping("/books")
    public Book updateBook(@RequestBody Book book) {

        // Check if update target exists
        Book result = service.getBookById(book.getId());

        if (result == null) {
            logger.warn("Book does not exist.");
            return null;
        }

        logger.info("Book is updated.");

        return service.updateBook(book);
    }

    @PutMapping("/books/purchase/{id}")
    public boolean purchaseBookById(@PathVariable long id) {

        Book result = service.getBookById(id);


        // Check if book exists and if it's in stock
        if (result == null || result.getQuantity() <= 0) {
            logger.warn("Book is not in stock.");

            return false;
        }
        // Record purchase by decrementing quantity by 1
        result.setQuantity(result.getQuantity() - 1);
        if (service.updateBook(result) != null) {
            logger.info("Book has been purchased");
            return true;
        }
        logger.warn("Book has not been purchased");
        return false;
    }

    @DeleteMapping("/books/id/{id}")
    public boolean deleteBookById(@PathVariable long id) {

        int count = service.getAllBooks().size();
        service.deleteBookById(id);

        // If count changed, then delete was successful
        if (count != service.getAllBooks().size()) {
            logger.info("Book has been deleted");
            return true;
        }
        logger.warn("Book has not been deleted");
        return false;
    }

}
