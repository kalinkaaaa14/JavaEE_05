package com.example.demo.repository;

import com.example.demo.entity.BookE;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import javax.persistence.EntityManager;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class BookRepository {
    private final EntityManager entityManager;


    public BookE saveNewBook(final BookE newBook) {
        return entityManager.merge(newBook);
    }


    public List<BookE> returnBooks(){
        return entityManager
                .createQuery("SELECT b FROM BookE b", BookE.class)
                .getResultList();
    }

    public List<BookE> findAll(final String search){
        return entityManager
                .createQuery("SELECT b FROM BookE b WHERE b.isbn LIKE :searchLine OR lower(b.title) LIKE :searchLine OR lower(b.author) LIKE :searchLine", BookE.class)
                .setParameter("searchLine", "%" + search.toLowerCase() + "%")
                .getResultList();
    }

    public BookE getByIsbn(String isbn) {
        return entityManager
                .createQuery("SELECT b FROM BookE b WHERE b.isbn = :isbn", BookE.class)
                .setParameter("isbn", isbn)
                .getSingleResult();
    }

}
