package com.excilys.loic.collection.binding.mapper;

import com.excilys.loic.collection.binding.EditorDTO;
import com.excilys.loic.collection.model.Editor;
import org.springframework.stereotype.Component;

@Component
public class EditorMapper {

    public Editor dtoToEditor(EditorDTO editorDTO){
        return new Editor(editorDTO.getId(), null, editorDTO.getName());
    }

    public EditorDTO editortoDTO(Editor editor){
        return new EditorDTO(editor.getId(), editor.getName());
    }


}
