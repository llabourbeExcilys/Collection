package com.excilys.loic.collection.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class Serie {

    @Id
    @GeneratedValue
    private long id;

    @OneToMany(mappedBy = "serie")
    private List<Book> mangas;
    
    @OneToMany(mappedBy = "serie")
    private List<Book> fanbooks;
    
    @OneToMany(mappedBy = "serie")
    private List<Book> artbooks;

    @ManyToMany
    private List<Author> author;

    @Column(nullable = false)
    private String title;

    @ManyToOne
    private Editor editor;
    private Boolean completed;

    public Serie(long id, List<Book> mangas, List<Book> fanbooks, List<Book> artbooks, List<Author> author, String title, Editor editor, Boolean completed) {
        this.id = id;
        this.mangas = mangas;
        this.fanbooks = fanbooks;
        this.artbooks = artbooks;
        this.author = author;
        this.title = title;
        this.editor = editor;
        this.completed = completed;
    }


    public Serie() {
    }

    public List<Book> getMangas() {
        return mangas;
    }

    public Serie setMangas(List<Book> mangas) {
        this.mangas = mangas;
        return this;
    }

    public List<Book> getFanbooks() {
        return fanbooks;
    }

    public Serie setFanbooks(List<Book> fanbooks) {
        this.fanbooks = fanbooks;
        return this;
    }

    public List<Book> getArtbooks() {
        return artbooks;
    }

    public Serie setArtbooks(List<Book> artbooks) {
        this.artbooks = artbooks;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public Serie setTitle(String title) {
        this.title = title;
        return this;
    }

    public Editor getEditor() {
        return editor;
    }

    public Serie setEditor(Editor editor) {
        this.editor = editor;
        return this;
    }

    public List<Author> getAuthor() {
        return author;
    }

    public Serie setAuthor(List<Author> author) {
        this.author = author;
        return this;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public Serie setCompleted(Boolean completed) {
        this.completed = completed;
        return this;
    }

    public long getId() {
        return id;
    }

    public Serie setId(long id) {
        this.id = id;
        return this;
    }
}
