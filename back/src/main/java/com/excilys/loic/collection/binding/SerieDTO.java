package com.excilys.loic.collection.binding;

import java.util.List;

public class SerieDTO {

    private Long id;
    private String title;
    private EditorDTO editor;
    private List<GenreDTO> genres;
    private List<AuthorDTO> authors;
    private String edition;
    private int owned;
    private int published;
    private String defaultColor;


    public SerieDTO() {
    }

    public SerieDTO(Long id, String title, String edition, int owned, int published,  String defaultColor) {
        this.id = id;
        this.title = title;
        this.edition = edition;
        this.owned = owned;
        this.published = published;
        this.defaultColor = defaultColor;
    }

    public Long getId() {
        return id;
    }

    public SerieDTO setId(Long id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public SerieDTO setTitle(String title) {
        this.title = title;
        return this;
    }

    public EditorDTO getEditor() {
        return editor;
    }

    public SerieDTO setEditor(EditorDTO editor) {
        this.editor = editor;
        return this;
    }

    public String getEdition() {
        return edition;
    }

    public SerieDTO setEdition(String edition) {
        this.edition = edition;
        return this;
    }

    public int getOwned() {
        return owned;
    }

    public SerieDTO setOwned(int owned) {
        this.owned = owned;
        return this;
    }

    public int getPublished() {
        return published;
    }

    public SerieDTO setPublished(int published) {
        this.published = published;
        return this;
    }

    public List<GenreDTO> getGenres() {
        return genres;
    }

    public SerieDTO setGenres(List<GenreDTO> genres) {
        this.genres = genres;
        return this;
    }

    public List<AuthorDTO> getAuthors() {
        return authors;
    }

    public SerieDTO setAuthors(List<AuthorDTO> authors) {
        this.authors = authors;
        return this;
    }

    public String getDefaultColor() {
        return defaultColor;
    }

    public SerieDTO setDefaultColor(String defaultColor) {
        this.defaultColor = defaultColor;
        return this;
    }
}
