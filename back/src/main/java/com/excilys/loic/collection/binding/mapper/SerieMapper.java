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

    @Autowired
    public SerieMapper(Service service, EditorMapper editorMapper) {
        this.service = service;
        this.editorMapper = editorMapper;
    }

    public Serie dtoToSerie(SerieDTO serieDTO){
        return new Serie(serieDTO.getId(),
                null,
                null,
                null,
                null,
                serieDTO.getTitle(),
                this.service.findEditorById(serieDTO.getEditorId()).orElse(null),
                serieDTO.getGenreIds().stream().map(genreId -> this.service.findGenreById(genreId).orElse(null)).collect(Collectors.toList()),
                serieDTO.getEdition(),
                serieDTO.getOwned(),
                serieDTO.getPublished());
    }

    public SerieDTO serieToDTO(Serie serie){


        SerieDTO serieDTO = new SerieDTO(serie.getId(),
                serie.getTitle(),
                serie.getEditor().getId(),
                serie.getEditor().getName(),
                serie.getEdition(),
                serie.getOwned(),
                serie.getPublished());
        serieDTO.setGenreIds(serie.getGenres().stream().map(genre -> genre.getId()).collect(Collectors.toList()));
        return serieDTO;
    }

}
