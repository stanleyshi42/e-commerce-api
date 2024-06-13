package com.example.e_commerce_api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import com.example.e_commerce_api.controller.BookController;
import com.example.e_commerce_api.entity.Book;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class BookControllerTest {

    @Autowired
    BookController controller;

    @Test
    void contextLoads() {
        assertThat(controller).isNotNull();
    }


    @Test
        // Test basic CRUD operations
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

    @Test
    void addBookTest() {

        // Test adding book with duplicate title
        Book newBook = new Book(0, "Test", "Author", 10);
        Book newBook2 = new Book(0, "Test", "Author 2", 1);

        controller.addBook(newBook);
        assertNull(controller.addBook(newBook2));

    }

}
