package com.excilys.loic.collection.binding;

import java.time.LocalDate;

public class BookDTO {

    private Long id;
    private String title;
    private LocalDate releaseDate;
    private String color;
    private Boolean owned;
    private Integer volume;

    public BookDTO() {
    }

    public BookDTO(Long id, String title, LocalDate releaseDate, String color, Boolean owned, Integer volume) {
        this.id = id;
        this.title = title;
        this.releaseDate = releaseDate;
        this.color = color;
        this.owned = owned;
        this.volume = volume;
    }

    public Long getId() {
        return id;
    }

    public BookDTO setId(Long id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public BookDTO setTitle(String title) {
        this.title = title;
        return this;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public BookDTO setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
        return this;
    }

    public String getColor() {
        return color;
    }

    public BookDTO setColor(String color) {
        this.color = color;
        return this;
    }

    public Boolean getOwned() {
        return owned;
    }

    public BookDTO setOwned(Boolean owned) {
        this.owned = owned;
        return this;
    }

    public Integer getVolume() {
        return volume;
    }

    public BookDTO setVolume(Integer volume) {
        this.volume = volume;
        return this;
    }
}
