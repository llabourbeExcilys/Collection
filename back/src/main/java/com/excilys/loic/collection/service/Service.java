package com.excilys.loic.collection.service;

import com.excilys.loic.collection.binding.*;
import com.excilys.loic.collection.binding.mapper.AuthorMapper;
import com.excilys.loic.collection.binding.mapper.EditorMapper;
import com.excilys.loic.collection.binding.mapper.GenreMapper;
import com.excilys.loic.collection.dao.*;
import com.excilys.loic.collection.model.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@org.springframework.stereotype.Service
public class Service {

    private AuthorDAO authorDAO;
    private BookDAO bookDAO;
    private EditorDAO editorDAO;
    private GenreDAO genreDAO;
    private SerieDAO serieDAO;

    private GenreMapper genreMapper;
    private AuthorMapper authorMapper;
    private EditorMapper editorMapper;


    @Autowired
    public Service(AuthorDAO authorDAO, BookDAO bookDAO, EditorDAO editorDAO, GenreDAO genreDAO, SerieDAO serieDAO, GenreMapper genreMapper, AuthorMapper authorMapper, EditorMapper editorMapper) {
        this.authorDAO = authorDAO;
        this.bookDAO = bookDAO;
        this.editorDAO = editorDAO;
        this.genreDAO = genreDAO;
        this.serieDAO = serieDAO;
        this.genreMapper = genreMapper;
        this.authorMapper = authorMapper;
        this.editorMapper = editorMapper;
    }

    //// Authors ////

    public List<AuthorDTO> getAuthorsDTO(){
        return this.authorDAO.findAllBy();
    }

    public List<Author> getAuthorsBySerieId(long id){
        return this.serieDAO.findAuthorBySerieId(id);
    }

    public List<Author> getAuthorsByIdIn(List<Long> ids){
        return this.authorDAO.findAllById(ids);
    }


    public Optional<AuthorDTO> findAuthorDTOById(long id){
        return this.authorDAO.findDTOById(id);
    }

    public Optional<Author> findAuthorById(long id) {
        return this.authorDAO.findById(id);
    }

    public Author getAuthorById(long id) {
        return this.authorDAO.getOne(id);
    }

    public boolean doesAuthorExistById(long id) {
        return authorDAO.existsById(id);
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

    public List<BookDTO> getBooks(){
        return this.bookDAO.findAllBy();
    }
    public Optional<BookDTO> findBookDTOById(long id){
        return this.bookDAO.findDTOById(id);
    }
    public Optional<Book> findBookById(long id){
        return this.bookDAO.findById(id);
    }
    public Book getBookById(long id){
        return this.bookDAO.getOne(id);
    }
    public List<Book> findMangasBySerieId(long id){
        return this.serieDAO.findMangasBySerieId(id);
    }
    public boolean doesBookExistById(long id) {
        return bookDAO.existsById(id);
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

    public List<EditorDTO> getEditorsDTO() {
        return this.editorDAO.findAllBy();
    }

    public Optional<Editor> findEditorById(long id){
        return this.editorDAO.findById(id);
    }

    public Editor getEditorById(long id){
        return this.editorDAO.getOne(id);
    }

    public Editor getEditorBySerieId(long id){
        return this.serieDAO.findEditorBySerieId(id);
    }


    public Optional<EditorDTO> findEditorDTOById(long id){
        return this.editorDAO.findDTOById(id);
    }

    public boolean doesEditorExistById(long id) {
        return editorDAO.existsById(id);
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


    public List<GenreDTO> getGenresDTO(){
        return this.genreDAO.findAllBy();
    }

    public List<Genre> getGenreBySerieId(long id){
            return this.serieDAO.findGenreBySerieId(id);
    }

    public List<Genre> getGenresByIdIn(List<Long> ids) {
        return this.genreDAO.findAllById(ids);
    }

    public Optional<Genre> findGenreById(long id){
        return this.genreDAO.findById(id);
    }

    public boolean doesGenreExistById(long id) {
        return genreDAO.existsById(id);
    }

    public Optional<GenreDTO> findGenreDTOById(long id){
        return this.genreDAO.findDTOById(id);
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
    
    //// Serie ////

    public List<SerieDTO> getSeriesDTO(){
        List<SerieDTO> seriesDTO = this.serieDAO.findAllBy();
        for(SerieDTO serieDTO : seriesDTO){
            Long serieId = serieDTO.getId();
            List<Genre> genres = this.getGenreBySerieId(serieId);
            List<GenreDTO> genreDTOS = genres.stream().map(this.genreMapper::genreToDTO).collect(Collectors.toList());
            serieDTO.setGenres(genreDTOS);

            List<Author> authors = this.getAuthorsBySerieId(serieId);
            List<AuthorDTO> authorDTOS = authors.stream().map(this.authorMapper::authorToDTO).collect(Collectors.toList());
            serieDTO.setAuthors(authorDTOS);

            Editor editor = this.getEditorBySerieId(serieId);
            serieDTO.setEditor(this.editorMapper.editortoDTO(editor));
        }
        return seriesDTO;
    }

    public Optional<Serie> findSerieById(long id){
        return this.serieDAO.findById(id);
    }

    public Serie getSerieById(long id){
        return this.serieDAO.getOne(id);
    }

    public Optional<SerieDTO> findSerieDTOById(long id) {
        return this.serieDAO.findDTOById(id);
    }

    public List<Serie> getSeriesByAuthorId(long id){
        return this.serieDAO.findByAuthorsId(id);
    }

    public List<Serie> getSeriesByGenresId(long id){
        return this.serieDAO.findByGenresId(id);
    }


    public boolean doesSerieExistById(long id) {
        return serieDAO.existsById(id);
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
