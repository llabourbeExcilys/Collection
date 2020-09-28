package com.excilys.loic.collection.dao;

import com.excilys.loic.collection.binding.SerieDTO;
import com.excilys.loic.collection.model.Serie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SerieDAO extends JpaRepository<Serie, Long> {

    List<Serie> findByAuthorId(long id);

    @Query("Select new com.excilys.loic.collection.binding.SerieDTO(serie.id, serie.title, serie.editor.id, serie.editor.name, serie.edition, serie.owned, serie.published) FROM Serie serie")
    List<SerieDTO> findAllBy();

    @Query("Select new com.excilys.loic.collection.binding.SerieDTO(serie.id, serie.title, serie.editor.id, serie.editor.name, serie.edition, serie.owned, serie.published) FROM Serie serie WHERE serie.id = ?1")
    Optional<SerieDTO> findDTOById(long id);
}
