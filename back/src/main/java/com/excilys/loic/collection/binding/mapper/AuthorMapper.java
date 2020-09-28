package com.excilys.loic.collection.binding.mapper;

import com.excilys.loic.collection.binding.AuthorDTO;
import com.excilys.loic.collection.model.Author;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class AuthorMapper {

    public AuthorDTO authorToDTO(Author author){
        return new AuthorDTO(author.getId(), author.getFirstName(), author.getLastName());
    }

    public Author DTOToAuthor(AuthorDTO authorDTO){
        return new Author(authorDTO.getId(), authorDTO.getFirstName(), authorDTO.getLastName(), new ArrayList<>());
    }

}
