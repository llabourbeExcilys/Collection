package com.excilys.loic.collection.dao;

import com.excilys.loic.collection.binding.GenreDTO;
import com.excilys.loic.collection.model.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface GenreDAO  extends JpaRepository<Genre, Long> {

    @Query("Select new com.excilys.loic.collection.binding.GenreDTO(genre.id, genre.name, genre.description) " +
            "FROM Genre genre")
    List<GenreDTO> findAllBy();

    @Query("Select new com.excilys.loic.collection.binding.GenreDTO(genre.id, genre.name, genre.description) " +
            "FROM Genre genre " +
            "WHERE genre.id = ?1")
    Optional<GenreDTO> findDTOById(long id);


}
