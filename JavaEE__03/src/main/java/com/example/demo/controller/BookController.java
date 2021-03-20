package com.example.demo.controller;

import com.example.demo.entity.BookE;
import com.example.demo.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
public class BookController {

    BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/")
    public String getMain(Model model) {
        model.addAttribute("books", bookService.returnAllBooks());
        return "main";
    }

    @ResponseBody
    @PostMapping("/add-book")
    public ResponseEntity<String> addBookPost(@RequestBody BookE book) {
        String isValid = bookService.createNewBook(book);
        int status = isValid.equals("OK") ? 200 : 400;
        return ResponseEntity.status(status).body(isValid);
    }

    @ResponseBody
    @GetMapping("/get-books")
    public ResponseEntity<List<BookE>> getAllBooks(@RequestParam(name = "getBy", required = false) String search) {
            return ResponseEntity.status(200).body(bookService.returnAllBooks());
    }

    @ResponseBody
    @GetMapping("/search-books")
    public ResponseEntity<List<BookE>> searchBook(@RequestParam(name = "getBy", required = false) String search) {
       if(search == null) {
            return ResponseEntity.status(200)
                                 .header("h1","Getting all books")
                                 .body(bookService.returnAllBooks());
       }
        return ResponseEntity.ok().body(bookService.findBooks(search));
   }

    @GetMapping("/book/{isbn}")
    public String getBook(@PathVariable String isbn, Model model) {
        BookE book = bookService.getBookByIsbn(isbn);
        model.addAttribute("book", book);
        return "view-book";
    }

}
