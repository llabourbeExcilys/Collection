package com.excilys.loic.collection.binding;

public class SerieDTO {

    private long id;
    private String title;
    private EditorDTO editorDTO;
    private String edition;
    private int owned;
    private int published;

    public SerieDTO() {
    }

    public SerieDTO(long id, String title, EditorDTO editorDTO, String edition, int owned, int published) {
        this.id = id;
        this.title = title;
        this.editorDTO = editorDTO;
        this.edition = edition;
        this.owned = owned;
        this.published = published;
    }

    public long getId() {
        return id;
    }

    public SerieDTO setId(long id) {
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

    public EditorDTO getEditorDTO() {
        return editorDTO;
    }

    public SerieDTO setEditorDTO(EditorDTO editorDTO) {
        this.editorDTO = editorDTO;
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
}
