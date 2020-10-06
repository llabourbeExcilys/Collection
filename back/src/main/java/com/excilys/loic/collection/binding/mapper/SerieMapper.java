package com.excilys.loic.collection.binding.mapper;

import com.excilys.loic.collection.binding.SerieDTO;
import com.excilys.loic.collection.model.Serie;
import com.excilys.loic.collection.service.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class SerieMapper {

    private Service service;
    private EditorMapper editorMapper;
    private GenreMapper genreMapper;
    private AuthorMapper authorMapper;

    @Autowired
    public SerieMapper(Service service, EditorMapper editorMapper, GenreMapper genreMapper, AuthorMapper authorMapper) {
        this.service = service;
        this.editorMapper = editorMapper;
        this.genreMapper = genreMapper;
        this.authorMapper = authorMapper;
    }

    public Serie dtoToSerie(SerieDTO serieDTO){
        return new Serie(serieDTO.getId(),
                null,
                null,
                null,
                serieDTO.getAuthors().stream().map(author -> this.service.findAuthorById(author.getId()).orElse(null)).collect(Collectors.toList()),
                serieDTO.getTitle(),
                this.service.findEditorById(serieDTO.getEditor().getId()).orElse(null),
                serieDTO.getGenres().stream().map(genre -> this.service.findGenreById(genre.getId()).orElse(null)).collect(Collectors.toList()),
                serieDTO.getEdition(),
                serieDTO.getOwned(),
                serieDTO.getPublished());
    }

    public SerieDTO serieToDTO(Serie serie){


        SerieDTO serieDTO = new SerieDTO(serie.getId(),
                serie.getTitle(),
                serie.getEdition(),
                serie.getOwned(),
                serie.getPublished());
        serieDTO.setEditor(this.editorMapper.editortoDTO(serie.getEditor()));
        serieDTO.setGenres(serie.getGenres().stream().map(this.genreMapper::genreToDTO).collect(Collectors.toList()));
        serieDTO.setAuthors(serie.getAuthors().stream().map(this.authorMapper::authorToDTO).collect(Collectors.toList()));
        return serieDTO;
    }

}
