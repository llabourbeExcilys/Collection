package com.excilys.loic.collection.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Genre {

    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false)
    private String name;

    private String description;

    public Genre(long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public Genre(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Genre() {
    }

    public String getName() {
        return name;
    }

    public Genre setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Genre setDescription(String description) {
        this.description = description;
        return this;
    }

    public long getId() {
        return id;
    }

    public Genre setId(long id) {
        this.id = id;
        return this;
    }
}
