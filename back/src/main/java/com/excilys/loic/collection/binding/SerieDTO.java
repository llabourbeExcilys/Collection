package com.excilys.loic.collection.binding;

public class SerieDTO {

    private Long id;
    private String title;
    private Long editorId;
    private String editorName;
    private String edition;
    private int owned;
    private int published;

    public SerieDTO() {
    }

    public SerieDTO(Long id, String title, Long editorId, String editorName, String edition, int owned, int published) {
        this.id = id;
        this.title = title;
        this.editorId = editorId;
        this.editorName = editorName;
        this.edition = edition;
        this.owned = owned;
        this.published = published;
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

    public Long getEditorId() {
        return editorId;
    }

    public SerieDTO setEditorId(Long editorId) {
        this.editorId = editorId;
        return this;
    }

    public String getEditorName() {
        return editorName;
    }

    public SerieDTO setEditorName(String editorName) {
        this.editorName = editorName;
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
