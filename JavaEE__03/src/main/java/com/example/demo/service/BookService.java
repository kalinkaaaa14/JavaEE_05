package com.example.demo.service;

import com.example.demo.entity.BookE;
import com.example.demo.repository.BookRepository;
import com.example.demo.validation.BookValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;
    private final BookValidator bookValidator;

    @EventListener
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {
        bookRepository.saveNewBook(BookE.builder().isbn("isbn1").title("Pride and Prejudice").author("Jane Austen").build());
        bookRepository.saveNewBook(BookE.builder().isbn("isbn2").title("Da Vinci Code").author("Dan Braun").build());
        bookRepository.saveNewBook(BookE.builder().isbn("isbn3").title("Harry Potter and the Deathly Hallows").author("J. K. Rowling").build());
    }

    @Transactional
    public String createNewBook(final BookE newBook) {
        log.info("Try to create new book: {}", newBook.getIsbn());
        String validatorResult = bookValidator.validateNewBook(newBook);
        if(validatorResult.equals("OK")) {
            final BookE book = bookRepository.saveNewBook(newBook);
            log.info("New book is created: {}", book);
        }
        return validatorResult;

    }

    @Transactional
    public List<BookE> returnAllBooks(){
        return  bookRepository.returnBooks();
    }

    @Transactional
    public List<BookE> findBooks(final String s){
        return bookRepository.findAll(s);
    }

    @Transactional
    public BookE getBookByIsbn(String isbn) {
        return bookRepository.getByIsbn(isbn);
    }

}

