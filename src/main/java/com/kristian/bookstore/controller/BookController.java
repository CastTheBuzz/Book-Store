package com.kristian.bookstore.controller;

import com.kristian.bookstore.repository.CategoryRepository;
import com.kristian.bookstore.model.Book;
import com.kristian.bookstore.service.BookDeletionException;
import com.kristian.bookstore.service.BookService;
import com.kristian.bookstore.service.DashboardStats;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class BookController {

    @Autowired
    private BookService bookService;
    
    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping("/books")
    public String listBooks(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(defaultValue = "id") String sortField,
            @RequestParam(defaultValue = "asc") String sortDir,
            Model model) {

        Page<Book> bookPage =
            bookService.getBooks(page, keyword, categoryId, sortField, sortDir);
        
        model.addAttribute("books", bookPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", bookPage.getTotalPages());
        model.addAttribute("keyword", keyword);
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir",
                sortDir.equals("asc") ? "desc" : "asc");
        model.addAttribute("categoryId", categoryId);
        model.addAttribute("categories", categoryRepository.findAll());

        return "books";
    }

    @GetMapping("/add-book")
    public String showAddBookForm(Model model) {

        model.addAttribute("book", new Book());
        model.addAttribute("categories", categoryRepository.findAll());

        return "add-book";
    }

    @PostMapping("/save-book")
    public String saveBook(
            @Valid @ModelAttribute("book") Book book,
            BindingResult result,
            @RequestParam("image") MultipartFile image,
            RedirectAttributes redirectAttributes,
            Model model) {

        if (result.hasErrors()) {
            model.addAttribute("categories", categoryRepository.findAll());
            return "add-book";
        }

        try {
            bookService.saveBookWithImage(book, image);
            return "redirect:/books";

        } catch (RuntimeException ex) {

            model.addAttribute("categories", categoryRepository.findAll());
            model.addAttribute("errorMessage", ex.getMessage());
            return "add-book";
        }
    }

    @GetMapping("/delete-book/{id}")
    public String deleteBook(@PathVariable Long id,
                             RedirectAttributes redirectAttributes) {

        try {
            bookService.deleteBook(id);
            redirectAttributes.addFlashAttribute("successMessage",
                    "Book deleted successfully.");

        } catch (BookDeletionException ex) {

            redirectAttributes.addFlashAttribute("errorMessage",
                    ex.getMessage());
        }

        return "redirect:/books";
    }

    @GetMapping("/edit-book/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {

        Book book = bookService.getBookById(id);

        model.addAttribute("book", book);
        model.addAttribute("categories", categoryRepository.findAll());
        return "edit-book";
    }

    @PostMapping("/update-book")
    public String updateBook(
            @Valid @ModelAttribute("book") Book book,
            BindingResult result) {

        if (result.hasErrors()) {
            return "edit-book";
        }

        bookService.saveBook(book);
        return "redirect:/books";
    }

    @GetMapping("/book/{id}")
    public String viewBookDetails(@PathVariable Long id, Model model) {

        Book book = bookService.getBookById(id);

        model.addAttribute("book", book);
        return "book-details";
    }
    
    @GetMapping("/admin/dashboard")
    public String showDashboard(Model model) {

        DashboardStats stats = bookService.getDashboardStats();

        model.addAttribute("stats", stats);

        return "dashboard";
    }
}