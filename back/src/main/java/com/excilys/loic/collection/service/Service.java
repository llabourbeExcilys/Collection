package com.excilys.loic.collection.service;

import com.excilys.loic.collection.dao.*;
import com.excilys.loic.collection.model.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@org.springframework.stereotype.Service
public class Service {

    private AuthorDAO authorDAO;
    private BookDAO bookDAO;
    private EditorDAO editorDAO;
    private GenreDAO genreDAO;
    private SerieDAO serieDAO;

    @Autowired
    public Service(AuthorDAO authorDAO, BookDAO bookDAO, EditorDAO editorDAO, GenreDAO genreDAO, SerieDAO serieDAO) {
        this.authorDAO = authorDAO;
        this.bookDAO = bookDAO;
        this.editorDAO = editorDAO;
        this.genreDAO = genreDAO;
        this.serieDAO = serieDAO;
    }

    //// Authors ////

    public List<Author> getAuthors(){
        return this.authorDAO.findAll();
    }

    public void addAuthor(Author author) {
        this.authorDAO.save(author);
    }

    public void deleteAuthorById(long id) {
        this.authorDAO.deleteById(id);
    }

    public void updateAuthor(Author author) {
        this.authorDAO.save(author);
    }

    //// Books ////

    public List<Book> getBooks(){
        return this.bookDAO.findAll();
    }

    public void addBook(Book book) {
        this.bookDAO.save(book);
    }

    public void deleteBookById(long id) {
        this.bookDAO.deleteById(id);
    }

    public void updateBook(Book book) {
        this.bookDAO.save( book);
    }

    //// Editor ////

    public List<Editor> getEditors(){
        return this.editorDAO.findAll();
    }

    public void addEditor(Editor editor) {
        this.editorDAO.save(editor);
    }

    public void deleteEditorById(long id) {
        this.editorDAO.deleteById(id);
    }

    public void updateEditor(Editor editor) {
        this.editorDAO.save( editor);
    }

    //// Genre ////

    public List<Genre> getGenres(){
        return this.genreDAO.findAll();
    }

    public void addGenre(Genre genre) {
        this.genreDAO.save(genre);
    }

    public void deleteGenreById(long id) {
        this.genreDAO.deleteById(id);
    }

    public void updateGenre(Genre genre) {
        this.genreDAO.save( genre);
    }
    
    //// SerieDAO ////

    public List<Serie> getSeries(){
        return this.serieDAO.findAll();
    }

    public void addSerie(Serie serie) {
        this.serieDAO.save(serie);
    }

    public void deleteSerieById(long id) {
        this.serieDAO.deleteById(id);
    }

    public void updateSerie(Serie serie) {
        this.serieDAO.save( serie);
    }
    
    
}
