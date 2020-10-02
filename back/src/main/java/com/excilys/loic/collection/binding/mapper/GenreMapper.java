package com.excilys.loic.collection.binding.mapper;

import com.excilys.loic.collection.binding.GenreDTO;
import com.excilys.loic.collection.model.Genre;
import org.springframework.stereotype.Component;

@Component
public class GenreMapper {

    public Genre dtoToGenre(GenreDTO genreDTO){
        return new Genre(genreDTO.getId(), genreDTO.getName(), genreDTO.getDescription(), null);
    }

    public GenreDTO genreToDTO( Genre genre){
        return new GenreDTO(genre.getId(), genre.getName(), genre.getDescription());
    }

}
