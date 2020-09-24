package com.excilys.loic.collection.dao;

import com.excilys.loic.collection.binding.AuthorDTO;
import com.excilys.loic.collection.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AuthorDAO extends JpaRepository<Author, Long> {

    List<AuthorDTO> findAllBy();

    Optional<AuthorDTO> findDTOById(long id);

}
