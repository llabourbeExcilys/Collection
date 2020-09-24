package com.excilys.loic.collection.binding;

public class GenreDTO {

    private long id;

    private String name;

    private String description;

    public GenreDTO(long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public GenreDTO(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public GenreDTO() {
    }

    public String getName() {
        return name;
    }

    public GenreDTO setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public GenreDTO setDescription(String description) {
        this.description = description;
        return this;
    }

    public long getId() {
        return id;
    }

    public GenreDTO setId(long id) {
        this.id = id;
        return this;
    }


}
