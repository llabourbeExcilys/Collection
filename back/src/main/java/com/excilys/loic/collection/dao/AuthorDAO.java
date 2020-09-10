package com.excilys.loic.collection.dao;

import com.excilys.loic.collection.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorDAO extends JpaRepository<Author, Long> {



}
