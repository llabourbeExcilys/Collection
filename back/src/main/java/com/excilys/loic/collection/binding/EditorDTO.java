package com.excilys.loic.collection.binding;

public class EditorDTO {

    private long id;

    private String name;

    public EditorDTO(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public EditorDTO(String name) {
        this.name = name;
    }

    public EditorDTO() {
    }

    public String getName() {
        return name;
    }

    public EditorDTO setName(String name) {
        this.name = name;
        return this;
    }

    public long getId() {
        return id;
    }

    public EditorDTO setId(long id) {
        this.id = id;
        return this;
    }
}
