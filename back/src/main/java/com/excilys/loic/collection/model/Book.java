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

    private int volume;

    @Column(columnDefinition = "DATE")
    private LocalDate releaseDate;

    @ManyToOne
    private Serie serie;

    public Book(String title, int volume, LocalDate releaseDate, Serie serie) {
        this.title = title;
        this.volume = volume;
        this.releaseDate = releaseDate;
        this.serie = serie;
    }

    public Book(String title, int volume, LocalDate releaseDate ) {
        this.title = title;
        this.volume = volume;
        this.releaseDate = releaseDate;
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

    public int getVolume() {
        return volume;
    }

    public Book setVolume(int volume) {
        this.volume = volume;
        return this;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public Book setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
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
