package com.excilys.loic.collection.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class Serie {

    @Id
    @GeneratedValue
    private Long id;

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

    private String edition;

    private int owned;

    private int published;


    public Serie(Long id, List<Book> mangas, List<Book> fanbooks, List<Book> artbooks, List<Author> author, String title, Editor editor, String edition, int owned, int published) {
        this.id = id;
        this.mangas = mangas;
        this.fanbooks = fanbooks;
        this.artbooks = artbooks;
        this.author = author;
        this.title = title;
        this.editor = editor;
        this.edition = edition;
        this.owned = owned;
        this.published = published;
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

    public int getOwned() {
        return owned;
    }

    public Serie setOwned(int owned) {
        this.owned = owned;
        return this;
    }

    public int getPublished() {
        return published;
    }

    public Serie setNumberPublished(int published) {
        this.published = published;

        return this;
    }

    public String getEdition() {
        return edition;
    }

    public Serie setEdition(String edition) {
        this.edition = edition;
        return this;
    }

    public Serie setPublished(int published) {
        this.published = published;
        return this;
    }

    public Long getId() {
        return id;
    }

    public Serie setId(Long id) {
        this.id = id;
        return this;
    }
}
