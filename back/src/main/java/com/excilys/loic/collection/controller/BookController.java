package com.excilys.loic.collection.controller;

import com.excilys.loic.collection.model.Author;
import com.excilys.loic.collection.model.Book;
import com.excilys.loic.collection.service.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/book")
public class BookController {

    private Service service;

    @Autowired
    public BookController(Service service) {
        this.service = service;
    }

    @GetMapping
    public List<Book> getAllBooks(){
        return service.getBooks();
    }

    @GetMapping("/{id}")
    public Book getBookById(@PathVariable long id){
        return service.getBookById(id).orElse(null);
    }

    @PostMapping
    public void postBook(@RequestBody Book book){
        this.service.addBook(book);
    }

    @PutMapping
    public void putBook(@RequestBody Book book){
        this.service.updateBook(book);
    }

    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable long id){
        this.service.deleteBookById(id);
    }
}
