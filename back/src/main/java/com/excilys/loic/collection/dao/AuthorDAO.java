package com.excilys.loic.collection.dao;

import com.excilys.loic.collection.binding.AuthorDTO;
import com.excilys.loic.collection.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AuthorDAO extends JpaRepository<Author, Long> {

    @Query("Select new com.excilys.loic.collection.binding.AuthorDTO(author.id, author.firstName, author.lastName) FROM Author author")
    List<AuthorDTO> findAllBy();

    @Query("Select new com.excilys.loic.collection.binding.AuthorDTO(author.id, author.firstName, author.lastName) FROM Author author WHERE author.id = ?1")
    Optional<AuthorDTO> findDTOById(Long id);

}
