package com.excilys.loic.collection.controller;

import com.excilys.loic.collection.binding.GenreDTO;
import com.excilys.loic.collection.binding.mapper.GenreMapper;
import com.excilys.loic.collection.model.Genre;
import com.excilys.loic.collection.service.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/genre")
public class GenreController {

    private Service service;

    private GenreMapper genreMapper;

    @Autowired
    public GenreController(Service service, GenreMapper genreMapper) {
        this.service = service;
        this.genreMapper = genreMapper;
    }

    @GetMapping
    public List<GenreDTO> getAllGenres(){
        return service.getGenresDTO();
    }

    @GetMapping("/{id}")
    public GenreDTO getGenreById(@PathVariable long id){
        return service.findGenreDTOById(id).orElse(null);
    }

    @PostMapping
    public void  postGenre(@RequestBody GenreDTO genreDTO){
        Genre genre = genreMapper.dtoToGenre(genreDTO);
        genre.setId(null);
        this.service.addGenre(genre);
    }

    @PutMapping
    public void putGenre(@RequestBody GenreDTO genreDTO){
        this.service.updateGenre(genreMapper.dtoToGenre(genreDTO));
    }

    @DeleteMapping("/{id}")
    public void deleteGenre(@PathVariable long id){
        this.service.deleteGenreById(id);
    }
}
