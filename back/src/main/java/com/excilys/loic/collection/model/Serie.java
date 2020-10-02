package com.excilys.loic.collection.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class Serie {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String title;

    @OneToMany(mappedBy = "serie", cascade = CascadeType.ALL)
    private List<Book> mangas;

    @OneToMany(mappedBy = "serie", cascade = CascadeType.ALL)
    private List<Book> fanbooks;

    @OneToMany(mappedBy = "serie", cascade = CascadeType.ALL)
    private List<Book> artbooks;

    @ManyToOne
    private Editor editor;

    @ManyToMany
    @JoinTable(name="serie_author",
            joinColumns=@JoinColumn(name="serie_id"),
            inverseJoinColumns=@JoinColumn(name="author_id")
    )
    private List<Author> authors;

    @ManyToMany
    @JoinTable(name="serie_genre",
            joinColumns=@JoinColumn(name="serie_id"),
            inverseJoinColumns=@JoinColumn(name="genre_id")
    )
    private List<Genre> genres;

    private String edition;

    private int owned;

    private int published;


    public Serie(Long id, List<Book> mangas, List<Book> fanbooks, List<Book> artbooks, List<Author> authors, String title, Editor editor, List<Genre> genres, String edition, int owned, int published) {
        this.id = id;
        this.mangas = mangas;
        this.fanbooks = fanbooks;
        this.artbooks = artbooks;
        this.authors = authors;
        this.title = title;
        this.editor = editor;
        this.genres = genres;
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

    public List<Author> getAuthors() {
        return authors;
    }

    public Serie setAuthors(List<Author> authors) {
        this.authors = authors;
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

    public List<Genre> getGenres() {
        return genres;
    }

    public Serie setGenres(List<Genre> genres) {
        this.genres = genres;
        return this;
    }
}
