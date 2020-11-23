package com.excilys.loic.collection.controller;

import com.excilys.loic.collection.binding.AuthorDTO;
import com.excilys.loic.collection.binding.mapper.AuthorMapper;
import com.excilys.loic.collection.model.Author;
import com.excilys.loic.collection.model.Serie;
import com.excilys.loic.collection.service.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/author")
public class AuthorController {

    private Service service;
    private AuthorMapper authorMapper;

    @Autowired
    public AuthorController(Service service, AuthorMapper authorMapper) {
        this.service = service;
        this.authorMapper = authorMapper;
    }

    @GetMapping
    public List<AuthorDTO> getAllAuthors(){
        return service.getAuthorsDTO();
    }


    @GetMapping("/{id}/serie")
    public List<Serie> getAuthorSeries(@PathVariable long id){
        return service.getSeriesByAuthorId(id);
    }


    @GetMapping("/{id}")
    public AuthorDTO getAuthorById(@PathVariable long id){
        return service.findAuthorDTOById(id).orElse(null);
    }

    @PostMapping
    public void  postAuthor(@RequestBody AuthorDTO authorDTO){
        Author author = this.authorMapper.DTOToAuthor(authorDTO);
        author.setId(null);
        this.service.addAuthor(author);
    }

    @PutMapping("/{id}")
    public void putAuthor(@RequestBody AuthorDTO authorDTO, @PathVariable long id){
        if(authorDTO.getId() == id && this.service.doesAuthorExistById(authorDTO.getId())){
            Author author = this.service.getAuthorById(authorDTO.getId());
            author.setFirstName(authorDTO.getFirstName());
            author.setLastName(authorDTO.getLastName());
            this.service.updateAuthor(author);
        }
    }

    @DeleteMapping("/{id}")
    public void deleteAuthor(@PathVariable long id){
        this.service.deleteAuthorById(id);
    }


}
