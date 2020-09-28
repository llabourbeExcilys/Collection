package com.excilys.loic.collection.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class Editor {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToMany
    private List<Serie> series;

    @Column(nullable = false)
    private String name;

    public Editor(Long id, List<Serie> series, String name) {
        this.id = id;
        this.series = series;
        this.name = name;
    }

    public Editor() {
    }

    public String getName() {
        return name;
    }

    public Editor setName(String name) {
        this.name = name;
        return this;
    }

    public List<Serie> getSeries() {
        return series;
    }

    public Editor setSeries(List<Serie> series) {
        this.series = series;
        return this;
    }

    public Long getId() {
        return id;
    }

    public Editor setId(Long id) {
        this.id = id;
        return this;
    }
}
