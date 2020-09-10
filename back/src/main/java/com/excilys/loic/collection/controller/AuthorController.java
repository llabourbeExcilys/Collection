package com.excilys.loic.collection.controller;

import com.excilys.loic.collection.model.Author;
import com.excilys.loic.collection.service.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/author")
public class AuthorController {

    private Service service;

    @Autowired
    public AuthorController(Service service) {
        this.service = service;
    }

    @GetMapping
    public List<Author> getAllAuthors(){
        return service.getAuthors();
    }

    @PostMapping
    public void  postAuthor(@RequestBody Author author){
        this.service.addAuthor(author);
    }

    @PutMapping
    public void putAuthor(@RequestBody Author author){
        this.service.updateAuthor(author);
    }

    @DeleteMapping
    public void deleteAuthor(@RequestParam long id){
        this.service.deleteAuthorById(id);
    }


}
