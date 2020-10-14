package com.excilys.loic.collection.dao;

import com.excilys.loic.collection.binding.SerieDTO;
import com.excilys.loic.collection.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SerieDAO extends JpaRepository<Serie, Long> {

    List<Serie> findByAuthorsId(long id);

    @Query("Select new com.excilys.loic.collection.binding.SerieDTO(serie.id, serie.title, serie.edition, serie.owned, serie.published, serie.defaultColor) FROM Serie serie")
    List<SerieDTO> findAllBy();

    @Query("Select new com.excilys.loic.collection.binding.SerieDTO(serie.id, serie.title, serie.edition, serie.owned, serie.published, serie.defaultColor) FROM Serie serie WHERE serie.id = ?1")
    Optional<SerieDTO> findDTOById(long id);

    @Query("Select s.mangas FROM Serie s WHERE s.id = ?1")
    List<Book> findMangasBySerieId(long id);

    @Query("Select s.genres FROM Serie s WHERE s.id = ?1")
    List<Genre> findGenreBySerieId(long id);

    @Query("Select s.authors FROM Serie s WHERE s.id = ?1")
    List<Author> findAuthorBySerieId(long id);

    @Query("Select s.editor FROM Serie s WHERE s.id = ?1")
    Editor findEditorBySerieId(long id);

}
