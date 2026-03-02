package com.kristian.bookstore.controller;

import com.kristian.bookstore.dto.BookDTO;
import com.kristian.bookstore.dto.PagedResponse;
import com.kristian.bookstore.service.BookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/books")
public class BookRestController {

    @Autowired
    private BookService bookService;

    @GetMapping
    public PagedResponse<BookDTO> getBooks(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {

        return bookService.getBooksPaged(page, size);
    }
    
    @GetMapping("/{id}")
    public BookDTO getBookById(@PathVariable Long id) {
        return bookService.getBookDTOById(id);
    }
    
    @PostMapping(consumes = "multipart/form-data")
    public BookDTO createBook(
            @RequestPart("book") @Valid BookDTO bookDTO,
            @RequestPart("image") MultipartFile image) {

        return bookService.createBookWithImage(bookDTO, image);
    }
}
