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

    @OneToMany(targetEntity = Book.class, mappedBy = "serie", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Book> mangas;

    @OneToMany(targetEntity = Book.class, mappedBy = "serie", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Book> fanbooks;

    @OneToMany(targetEntity = Book.class, mappedBy = "serie", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Book> artbooks;

    @ManyToOne
    @JoinColumn(name="editor_id", nullable = false)
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

    private String defaultColor;

    public Serie(Long id, String title, List<Book> mangas, List<Book> fanbooks, List<Book> artbooks, Editor editor, List<Author> authors, List<Genre> genres, String edition, int owned, int published, String defaultColor) {
        this.id = id;
        this.title = title;
        this.mangas = mangas;
        this.fanbooks = fanbooks;
        this.artbooks = artbooks;
        this.editor = editor;
        this.authors = authors;
        this.genres = genres;
        this.edition = edition;
        this.owned = owned;
        this.published = published;
        this.defaultColor = defaultColor;
    }

    public Serie() {
    }

    public Serie addManga(Book book){
        this.mangas.add(book);
        book.setSerie(this);
        return this;
    }

    public Serie removeManga(Book book){
        this.mangas.remove(book);
        book.setSerie(null);
        return this;
    }

    public Serie removeManga(int index){
        Book book = this.mangas.get(index);
        book.setSerie(null);
        this.mangas.remove(index);
        return this;
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

    public Serie setPublished(int published) {
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

    public String getDefaultColor() {
        return defaultColor;
    }

    public Serie setDefaultColor(String defaultColor) {
        this.defaultColor = defaultColor;
        return this;
    }
}
