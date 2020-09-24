package com.excilys.loic.collection.dao;

import com.excilys.loic.collection.binding.EditorDTO;
import com.excilys.loic.collection.model.Editor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EditorDAO extends JpaRepository<Editor, Long> {


    Optional<EditorDTO> findDTOById(long id);

    List<EditorDTO> findAllBy();
}
