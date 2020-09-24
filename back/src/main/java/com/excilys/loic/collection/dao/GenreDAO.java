package com.excilys.loic.collection.dao;

import com.excilys.loic.collection.binding.GenreDTO;
import com.excilys.loic.collection.model.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GenreDAO  extends JpaRepository<Genre, Long> {

    List<GenreDTO> findAllBy();

    Optional<GenreDTO> findDTOById(long id);

}
