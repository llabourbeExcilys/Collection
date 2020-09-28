package com.excilys.loic.collection.controller;

import com.excilys.loic.collection.binding.BookDTO;
import com.excilys.loic.collection.binding.mapper.BookMapper;
import com.excilys.loic.collection.model.Book;
import com.excilys.loic.collection.service.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/book")
public class BookController {

    private Service service;

    private BookMapper bookMapper;

    @Autowired
    public BookController(Service service, BookMapper bookMapper) {
        this.service = service;
        this.bookMapper = bookMapper;
    }

    @GetMapping
    public List<BookDTO> getAllBooks(){
        return service.getBooks();
    }

    @GetMapping("/{id}")
    public BookDTO getBookById(@PathVariable long id){
        return service.findBookDTOById(id).orElse(null);
    }

    @PostMapping
    public void postBook(@RequestBody BookDTO bookDTO){
        Book book = bookMapper.DTOtoBook(bookDTO);
        book.setId(null);
        this.service.addBook(book);
    }

    @PutMapping
    public void putBook(@RequestBody BookDTO bookDTO){
        if(service.doesBookExistById(bookDTO.getId())){
            Book book = service.getBookById(bookDTO.getId());
            book.setVolume(bookDTO.getVolume());
            book.setTitle(bookDTO.getTitle());
            book.setReleaseDate(bookDTO.getReleaseDate());
            book.setOwned(bookDTO.getOwned());
            book.setColor(bookDTO.getColor());
            this.service.updateBook(book);
        }
    }

    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable long id){
        this.service.deleteBookById(id);
    }
}
