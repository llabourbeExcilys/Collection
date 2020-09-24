package com.excilys.loic.collection.controller;


import com.excilys.loic.collection.binding.SerieDTO;
import com.excilys.loic.collection.binding.mapper.SerieMapper;
import com.excilys.loic.collection.model.Editor;
import com.excilys.loic.collection.model.Serie;
import com.excilys.loic.collection.service.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/serie")
public class SerieController {

    private Service service;

    private SerieMapper serieMapper;

    @Autowired
    public SerieController(Service service, SerieMapper serieMapper) {
        this.service = service;
        this.serieMapper = serieMapper;
    }

    @GetMapping
    public List<SerieDTO> getAllSeriesDTO(){
        return service.getSeriesDTO();
    }

    @GetMapping("/{id}")
    public SerieDTO getSerieById(@PathVariable long id){
        return service.findSerieDTOById(id).orElse(null);
    }
    
    @PostMapping
    public void  postSerie(@RequestBody SerieDTO serieDTO){
        this.service.addSerie(serieMapper.dtoToSerie(serieDTO));
    }

    @PutMapping
    public void putSerie(@RequestBody SerieDTO serieDTO){
        if(this.service.doesSerieExistById(serieDTO.getId())){
            Serie serie = this.service.getSerieById(serieDTO.getId());
            serie.setNumberPublished(serieDTO.getPublished());
            if(serieDTO.getEditorDTO() != null){
                Optional<Editor> optionalEditor = this.service.findEditorById(serieDTO.getEditorDTO().getId());
                serie.setEditor(optionalEditor.orElse(null));
            }
            serie.setEdition(serieDTO.getEdition());
            serie.setOwned(serieDTO.getOwned());
            serie.setTitle(serieDTO.getTitle());

            this.service.updateSerie(serie);
        }
    }

    @DeleteMapping("/{id}")
    public void deleteSerie(@PathVariable long id){
        this.service.deleteSerieById(id);
    }
    
}
