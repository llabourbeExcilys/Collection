package com.excilys.loic.collection.dao;

import com.excilys.loic.collection.model.Genre;
import com.excilys.loic.collection.model.Serie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SerieDAO extends JpaRepository<Serie, Long> {
}
