package com.excilys.loic.collection.dao;

import com.excilys.loic.collection.model.Author;
import com.excilys.loic.collection.model.Editor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EditorDAO extends JpaRepository<Editor, Long> {
}
