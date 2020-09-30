package com.excilys.loic.collection.dao;

import com.excilys.loic.collection.binding.BookDTO;
import com.excilys.loic.collection.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BookDAO  extends JpaRepository<Book, Long> {

    @Query("Select new com.excilys.loic.collection.binding.BookDTO(book.id, book.title, book.releaseDate, book.color, book.owned, book.volume, book.type) FROM Book book")
    List<BookDTO> findAllBy();

    @Query("Select new com.excilys.loic.collection.binding.BookDTO(book.id, book.title, book.releaseDate, book.color, book.owned, book.volume, book.type) FROM Book book WHERE book.id = ?1")
    Optional<BookDTO> findDTOById(long id);


}
