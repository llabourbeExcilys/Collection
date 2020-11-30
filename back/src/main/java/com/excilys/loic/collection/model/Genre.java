package com.excilys.loic.collection.model;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.List;

@Entity
public class Genre {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String name;

    @Type(type = "text")
    private String description;

    @ManyToMany(mappedBy = "genres")
    private List<Serie> serie;

    public Genre(Long id, String name, String description, List<Serie> serie) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.serie = serie;
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

    public Long getId() {
        return id;
    }

    public Genre setId(Long id) {
        this.id = id;
        return this;
    }

    public List<Serie> getSerie() {
        return serie;
    }

    public Genre setSerie(List<Serie> serie) {
        this.serie = serie;
        return this;
    }
}
