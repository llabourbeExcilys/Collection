package com.excilys.loic.collection.dao;

import com.excilys.loic.collection.binding.EditorDTO;
import com.excilys.loic.collection.model.Editor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface EditorDAO extends JpaRepository<Editor, Long> {

    @Query("Select new com.excilys.loic.collection.binding.EditorDTO(editor.id, editor.name) FROM Editor editor")
    List<EditorDTO> findAllBy();

    @Query("Select new com.excilys.loic.collection.binding.EditorDTO(editor.id, editor.name) FROM Editor editor WHERE editor.id = ?1")
    Optional<EditorDTO> findDTOById(long id);
}
