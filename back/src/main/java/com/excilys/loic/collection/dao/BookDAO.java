package com.excilys.loic.collection.dao;

import com.excilys.loic.collection.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookDAO  extends JpaRepository<Book, Long> {
}
