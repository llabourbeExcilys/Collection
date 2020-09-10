package com.excilys.loic.collection.controller;


import com.excilys.loic.collection.model.Genre;
import com.excilys.loic.collection.model.Serie;
import com.excilys.loic.collection.service.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/serie")
public class SerieController {

    private Service service;

    @Autowired
    public SerieController(Service service) {
        this.service = service;
    }

    @GetMapping
    public List<Serie> getAllSeries(){
        return service.getSeries();
    }

    @PostMapping
    public void  postSerie(@RequestBody Serie serie){
        this.service.addSerie(serie);
    }

    @PutMapping
    public void putSerie(@RequestBody Serie serie){
        this.service.updateSerie(serie);
    }

    @DeleteMapping
    public void deleteSerie(@RequestParam long id){
        this.service.deleteSerieById(id);
    }
    
}
