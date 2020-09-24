package com.excilys.loic.collection.dao;

import com.excilys.loic.collection.binding.BookDTO;
import com.excilys.loic.collection.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookDAO  extends JpaRepository<Book, Long> {

    List<BookDTO> findAllBy();

    Optional<BookDTO> findDTOById(long id);


}
