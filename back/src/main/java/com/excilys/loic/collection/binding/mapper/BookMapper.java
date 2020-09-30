package com.excilys.loic.collection.binding.mapper;


import com.excilys.loic.collection.binding.BookDTO;
import com.excilys.loic.collection.model.Book;
import org.springframework.stereotype.Component;

@Component
public class BookMapper {


    public BookDTO booktoDTO(Book book){
        return new BookDTO(book.getId(), book.getTitle(), book.getReleaseDate(), book.getColor(), book.getOwned(), book.getVolume(), book.getType());
    }

    public Book DTOtoBook(BookDTO bookDTO){
        return new Book(bookDTO.getId(), bookDTO.getTitle(), bookDTO.getReleaseDate(), null, bookDTO.getColor(), bookDTO.getOwned(), bookDTO.getVolume(), bookDTO.getType());
    }

}
