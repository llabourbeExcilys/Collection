package com.excilys.loic.collection.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class Author {

    @Id
    @GeneratedValue
    private long id;

    private String firstName;
    private String lastName;

    @OneToMany(mappedBy = "editor")
    private List<Serie> series;


    public Author(long id, String firstName, String lastName, List<Serie> series) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.series = series;
    }

    public Author() {
    }

    public String getFirstName() {
        return firstName;
    }

    public Author setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public Author setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public long getId() {
        return id;
    }

    public Author setId(long id) {
        this.id = id;
        return this;
    }

    public List<Serie> getSeries() {
        return series;
    }

    public Author setSeries(List<Serie> series) {
        this.series = series;
        return this;
    }
}
