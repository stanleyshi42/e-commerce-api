package com.example.e_commerce_api;

import com.example.e_commerce_api.controller.BookController;
import com.example.e_commerce_api.entity.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class BookControllerTest {

    @Autowired
    BookController controller;

    @Test
    void contextLoads() {
        assertThat(controller).isNotNull();
    }

    @Test
    void controllerCRUDTest() {

        // Test create
        Book newBook = new Book(0, "Test", "Author", 10);
        Book actualBook = controller.addBook(newBook);
        assertThat(actualBook).isNotNull();

        // Test read
        assertEquals("Test", actualBook.getTitle());

        // Test update
        String expectedTitle = "New Title";
        actualBook.setTitle(expectedTitle);
        actualBook = controller.updateBook(actualBook);
        assertEquals(expectedTitle, actualBook.getTitle());

        // Test delete
        assertTrue(controller.deleteBookById(actualBook.getId()));


    }

}
