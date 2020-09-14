package com.excilys.loic.collection.controller;

import com.excilys.loic.collection.model.Book;
import com.excilys.loic.collection.model.Editor;
import com.excilys.loic.collection.service.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/editor")
public class EditorController {


    private Service service;

    @Autowired
    public EditorController(Service service) {
        this.service = service;
    }

    @GetMapping
    public List<Editor> getAllEditors(){
        return service.getEditors();
    }

    @GetMapping("/{id}")
    public Editor getEditorById(@PathVariable long id){
        return service.getEditorById(id).orElse(null);
    }

    @PostMapping
    public void  postEditor(@RequestBody Editor editor){
        this.service.addEditor(editor);
    }

    @PutMapping
    public void putEditor(@RequestBody Editor editor){
        this.service.updateEditor(editor);
    }


    @DeleteMapping("/{id}")
    public void deleteEditor(@PathVariable long id){
        this.service.deleteEditorById(id);
    }
}
