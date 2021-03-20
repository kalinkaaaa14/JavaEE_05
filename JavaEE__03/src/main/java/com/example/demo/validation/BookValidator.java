package com.example.demo.validation;

import com.example.demo.entity.BookE;
import com.example.demo.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class BookValidator {
    private final BookRepository bookRepository;

    public String validateNewBook(final BookE newBook) {
        List<BookE> books = bookRepository.returnBooks();

        if(newBook.getIsbn().isBlank() || newBook.getAuthor().isBlank() || newBook.getTitle().isBlank()){
            return "All fields should be filled!";
        }
        for (BookE book : books) {
            if(book.getIsbn().equals(newBook.getIsbn())){
                return "The book with such ISBN already exists. It should be unique. Try again.";
            }
        }
        return "OK";
    }

}
