package com.excilys.loic.collection.controller;

import com.excilys.loic.collection.model.Editor;
import com.excilys.loic.collection.model.Genre;
import com.excilys.loic.collection.service.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/genre")
public class GenreController {


    private Service service;

    @Autowired
    public GenreController(Service service) {
        this.service = service;
    }

    @GetMapping
    public List<Genre> getAllGenres(){
        return service.getGenres();
    }

    @GetMapping("/{id}")
    public Genre getGenreById(@PathVariable long id){
        return service.getGenreById(id).orElse(null);
    }

    @PostMapping
    public void  postGenre(@RequestBody Genre genre){
        this.service.addGenre(genre);
    }

    @PutMapping
    public void putGenre(@RequestBody Genre genre){
        this.service.updateGenre(genre);
    }

    @DeleteMapping("/{id}")
    public void deleteGenre(@PathVariable long id){
        this.service.deleteGenreById(id);
    }
}
