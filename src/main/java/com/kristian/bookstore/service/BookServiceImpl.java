package com.kristian.bookstore.service;

import com.kristian.bookstore.dto.BookDTO;
import com.kristian.bookstore.dto.PagedResponse;
import com.kristian.bookstore.exception.ResourceNotFoundException;
import com.kristian.bookstore.model.Book;
import com.kristian.bookstore.model.Category;
import com.kristian.bookstore.repository.BookRepository;
import com.kristian.bookstore.repository.CategoryRepository;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class BookServiceImpl implements BookService {
    
    @Value("${file.upload-dir}")
    private String uploadDir;

    @Autowired
    private BookRepository bookRepository;

    @Override
    public Page<Book> getBooks(int page,
                           String keyword,
                           Long categoryId,
                           String sortField,
                           String sortDir) {

        int pageSize = 5;

        Sort sort = sortDir.equalsIgnoreCase("asc") ?
                Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();

        Pageable pageable = PageRequest.of(page, pageSize, sort);

        boolean hasKeyword = keyword != null && !keyword.isEmpty();
        boolean hasCategory = categoryId != null;

        if (hasKeyword && hasCategory) {
            return bookRepository
                    .findByTitleContainingIgnoreCaseAndCategoryId(
                            keyword, categoryId, pageable);
        }
        else if (hasKeyword) {
            return bookRepository
                    .findByTitleContainingIgnoreCase(keyword, pageable);
        }
        else if (hasCategory) {
            return bookRepository
                    .findByCategoryId(categoryId, pageable);
        }
        else {
            return bookRepository.findAll(pageable);
        }
    }

    @Override
    public Book getBookById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid book ID: " + id));
    }

    @Override
    public void saveBook(Book book) {

        boolean exists = bookRepository
                .existsByTitleIgnoreCaseAndCategoryId(
                        book.getTitle(),
                        book.getCategory().getId());

        if (exists && book.getId() == null) {
            throw new RuntimeException(
                    "A book with this title already exists in this category.");
        }

        bookRepository.save(book);
    }

    @Override
    public void deleteBook(Long id) {

        Book book = bookRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("Book not found"));

        if (book.getQuantity() > 0) {
            throw new BookDeletionException(
                    "Cannot delete book with stock remaining. Reduce quantity first.");
        }

        bookRepository.delete(book);
    }
    
    @Autowired
    private CategoryRepository categoryRepository;
    
    @Override
    public DashboardStats getDashboardStats() {

        long totalBooks = bookRepository.count();
        long totalCategories = categoryRepository.count();
        long outOfStock = bookRepository.countByQuantity(0);

        Long totalStock = bookRepository.getTotalStock();
        if (totalStock == null) totalStock = 0L;

        Book mostExpensive = bookRepository.findTopByOrderByPriceDesc();

        return new DashboardStats(
                totalBooks,
                totalCategories,
                outOfStock,
                totalStock,
                mostExpensive
        );
    }
    
    @Override
    public List<BookDTO> getAllBooksAsDTO() {
        return bookRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .toList();
    }
    
    private BookDTO mapToDTO(Book book) {
        return new BookDTO(
                book.getId(),
                book.getTitle(),
                book.getAuthor(),
                book.getPrice(),
                book.getQuantity(),
                book.getCategory() != null
                        ? book.getCategory().getName()
                        : null,
                book.getDescription(),
                book.getImagePath()
        );
    }
    
    @Override
    public PagedResponse<BookDTO> getBooksPaged(int page, int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Book> bookPage = bookRepository.findAll(pageable);

        List<BookDTO> dtoList = bookPage.getContent()
                .stream()
                .map(this::mapToDTO)
                .toList();

        return new PagedResponse<>(
                dtoList,
                bookPage.getNumber(),
                bookPage.getSize(),
                bookPage.getTotalElements(),
                bookPage.getTotalPages()
        );
    }
    
    @Override
    public BookDTO getBookDTOById(Long id) {

        Book book = bookRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Book not found with id: " + id));

        return mapToDTO(book);
    }
    
    @Override
    public BookDTO createBookFromDTO(BookDTO dto){
        Category category = categoryRepository
                .findByName(dto.getCategoryName())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Category not found"));
        
        Book book = new Book();
        book.setTitle(dto.getTitle());
        book.setAuthor(dto.getAuthor());
        book.setPrice(dto.getPrice());
        book.setQuantity(dto.getQuantity());
        book.setCategory(category);
        
        Book saved = bookRepository.save(book);
        
        return mapToDTO(saved);
    }
    
    @Override
    public BookDTO createBookWithImage(BookDTO dto, MultipartFile image) {

        Category category = categoryRepository
                .findByName(dto.getCategoryName())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Category not found"));

        String fileName = System.currentTimeMillis() + "_" + image.getOriginalFilename();

        try {
            Path path = Paths.get(uploadDir, fileName);
            Files.copy(image.getInputStream(), path);
        } catch (IOException e) {
            throw new RuntimeException("Failed to store image");
        }

        Book book = new Book();
        book.setTitle(dto.getTitle());
        book.setAuthor(dto.getAuthor());
        book.setPrice(dto.getPrice());
        book.setQuantity(dto.getQuantity());
        book.setDescription(dto.getDescription());
        book.setCategory(category);
        book.setImagePath(fileName);

        Book saved = bookRepository.save(book);

        return mapToDTO(saved);
    }
    
    @Override
    public void saveBookWithImage(Book book, MultipartFile image) {

        if (!image.isEmpty()) {

            String fileName = System.currentTimeMillis()
                    + "_" + image.getOriginalFilename();

            try {
                Path path = Paths.get(uploadDir, fileName);
                Files.copy(image.getInputStream(), path);
                book.setImagePath(fileName);

            } catch (IOException e) {
                throw new RuntimeException("Failed to store image");
            }
        } else {
        // If editing and no new image selected,
        // keep the existing image from database

            if (book.getId() != null) {
                Book existingBook = bookRepository.findById(book.getId())
                        .orElseThrow(() ->
                                new RuntimeException("Book not found"));

                book.setImagePath(existingBook.getImagePath());
            }
        }

        bookRepository.save(book);
    }
}