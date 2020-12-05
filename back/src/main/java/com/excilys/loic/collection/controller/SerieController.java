package com.excilys.loic.collection.controller;


import com.excilys.loic.collection.binding.AuthorDTO;
import com.excilys.loic.collection.binding.GenreDTO;
import com.excilys.loic.collection.binding.SerieDTO;
import com.excilys.loic.collection.binding.mapper.SerieMapper;
import com.excilys.loic.collection.model.*;
import com.excilys.loic.collection.model.enums.Type;
import com.excilys.loic.collection.service.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
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
        Serie serie = serieMapper.dtoToSerie(serieDTO);
        serie.setId(null);
        List<Book> mangas = new ArrayList<>();
        for(int i = 1; i <= serie.getPublished(); i++){
            Book manga = new Book(null, serie.getTitle(), null, serie, "red", i<serie.getOwned(), i, Type.MANGA);
            mangas.add(manga);
        }
        serie.setMangas(mangas);
        this.service.addSerie(serie);
    }

    @PutMapping("/{id}")
    public void putSerie(@RequestBody SerieDTO serieDTO, @PathVariable long id){
        if(serieDTO.getId() == id && this.service.doesSerieExistById(serieDTO.getId())){
            Serie serie = this.service.getSerieById(serieDTO.getId());

            serie.setTitle(serieDTO.getTitle());
            serie.setEdition(serieDTO.getEdition());
            serie.setDefaultColor(serieDTO.getDefaultColor());

            // authors
            List<AuthorDTO> authorDTOS = serieDTO.getAuthors();
            List<Long> ids = authorDTOS.stream().map(authorDTO -> authorDTO.getId()).collect(Collectors.toList());
            List<Author> authors = this.service.getAuthorsByIdIn(ids);
            serie.setAuthors(authors);
            // editor
            Optional<Editor> optionalEditor = this.service.findEditorById(serieDTO.getEditor().getId());
            serie.setEditor(optionalEditor.orElse(null));

            // genres
            List<GenreDTO> genreDTOS = serieDTO.getGenres();
            ids = genreDTOS.stream().map(genreDTO -> genreDTO.getId()).collect(Collectors.toList());
            List<Genre> genres = this.service.getGenresByIdIn(ids);
            serie.setGenres(genres);

            //mangas
            List<Book> mangas = serie.getMangas();
            int oldPublished = serie.getPublished();
            int newOwned = serieDTO.getOwned();
            int newPublished= serieDTO.getPublished();

                // more mangas published
            if( oldPublished < newPublished){
                for (int i = oldPublished+1; i <= newPublished; i++){
                    mangas.add(new Book(null, serie.getTitle(), null, serie, "red", false, i, Type.MANGA));
                }
            }   // less manga published
            else if (newPublished < oldPublished){
                for (int i = oldPublished; i > newPublished; i--){
                    serie.removeManga(i-1);
                }
            }
            if(serie.getOwned() != newOwned){
                for (int i = 1; i <= newPublished; i++){
                    Book book = mangas.get(i - 1);
                    book.setOwned(i <= newOwned);
                }
            }
            serie.setOwned(serieDTO.getOwned());
            serie.setPublished(serieDTO.getPublished());


            this.service.updateSerie(serie);
        }
    }

    @DeleteMapping("/{id}")
    public void deleteSerie(@PathVariable long id){
        this.service.deleteSerieById(id);
    }
    
}
