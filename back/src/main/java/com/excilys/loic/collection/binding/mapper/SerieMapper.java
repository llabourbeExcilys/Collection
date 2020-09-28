package com.excilys.loic.collection.binding.mapper;

import com.excilys.loic.collection.binding.SerieDTO;
import com.excilys.loic.collection.model.Serie;
import com.excilys.loic.collection.service.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
                serieDTO.getEdition(),
                serieDTO.getOwned(),
                serieDTO.getPublished());
    }

    public SerieDTO serieToDTO(Serie serie){
        return new SerieDTO(serie.getId(), serie.getTitle(), serie.getEditor().getId(), serie.getEditor().getName(), serie.getEdition(), serie.getOwned(), serie.getPublished());
    }

}
