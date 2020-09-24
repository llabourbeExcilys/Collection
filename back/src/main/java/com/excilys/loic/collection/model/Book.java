package com.excilys.loic.collection.model;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Book {

    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "DATE")
    private LocalDate releaseDate;

    @ManyToOne
    private Serie serie;

    private String color;

    private Boolean owned;

    private Integer volume;

    public Book(long id, String title, LocalDate releaseDate, Serie serie, String color, Boolean owned, int volume) {
        this.id = id;
        this.title = title;
        this.releaseDate = releaseDate;
        this.serie = serie;
        this.color = color;
        this.owned = owned;
        this.volume = volume;
    }

    public Book() {
    }

    public Serie getSerie() {
        return serie;
    }

    public Book setSerie(Serie serie) {
        this.serie = serie;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public Book setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getColor() {
        return color;
    }

    public Book setColor(String color) {
        this.color = color;
        return this;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public Book setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
        return this;
    }

    public Boolean getOwned() {
        return owned;
    }

    public Book setOwned(Boolean owned) {
        this.owned = owned;
        return this;
    }

    public int getVolume() {
        return volume;
    }

    public Book setVolume(int volume) {
        this.volume = volume;
        return this;
    }

    public long getId() {
        return id;
    }

    public Book setId(long id) {
        this.id = id;
        return this;
    }
}
