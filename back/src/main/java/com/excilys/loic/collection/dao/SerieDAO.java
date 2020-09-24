package com.excilys.loic.collection.dao;

import com.excilys.loic.collection.binding.SerieDTO;
import com.excilys.loic.collection.model.Serie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SerieDAO extends JpaRepository<Serie, Long> {

    List<Serie> findByAuthorId(long id);

    List<SerieDTO> findAllBy();

    Optional<SerieDTO> findDTOById(long id);
}
