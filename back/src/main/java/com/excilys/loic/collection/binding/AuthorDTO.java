package com.excilys.loic.collection.binding;

public class AuthorDTO {

    private long id;
    private String firstName;
    private String lastName;


    public AuthorDTO(long id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public AuthorDTO() {
    }

    public long getId() {
        return id;
    }

    public AuthorDTO setId(long id) {
        this.id = id;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public AuthorDTO setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public AuthorDTO setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }
}
