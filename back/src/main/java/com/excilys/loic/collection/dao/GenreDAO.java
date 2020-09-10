package com.excilys.loic.collection.dao;

import com.excilys.loic.collection.model.Editor;
import com.excilys.loic.collection.model.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenreDAO  extends JpaRepository<Genre, Long> {
}
