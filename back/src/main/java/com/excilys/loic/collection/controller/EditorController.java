package com.excilys.loic.collection.controller;

import com.excilys.loic.collection.binding.EditorDTO;
import com.excilys.loic.collection.binding.mapper.EditorMapper;
import com.excilys.loic.collection.model.Editor;
import com.excilys.loic.collection.service.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/editor")
public class EditorController {


    private Service service;

    private EditorMapper editorMapper;

    @Autowired
    public EditorController(Service service,EditorMapper editorMapper) {
        this.service = service;
        this.editorMapper = editorMapper;
    }

    @GetMapping
    public List<EditorDTO> getAllEditors(){
        return service.getEditorsDTO();
    }

    @GetMapping("/{id}")
    public EditorDTO getEditorById(@PathVariable long id){
        return service.findEditorDTOById(id).orElse(null);
    }

    @PostMapping
    public void  postEditor(@RequestBody EditorDTO editorDTO){
        this.service.addEditor(editorMapper.dtoToEditor(editorDTO));
    }

    @PutMapping
    public void putEditor(@RequestBody EditorDTO editorDTO){
        if(this.service.doesEditorExistById(editorDTO.getId())){
            Editor editor = this.service.getEditorById(editorDTO.getId());
            editor.setName(editorDTO.getName());
            this.service.updateEditor(editor);
        }
    }


    @DeleteMapping("/{id}")
    public void deleteEditor(@PathVariable long id){
        this.service.deleteEditorById(id);
    }
}
