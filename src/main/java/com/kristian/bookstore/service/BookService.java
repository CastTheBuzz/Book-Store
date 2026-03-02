package com.kristian.bookstore.service;

import com.kristian.bookstore.dto.BookDTO;
import com.kristian.bookstore.dto.PagedResponse;
import com.kristian.bookstore.model.Book;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

public interface BookService {

    Page<Book> getBooks(int page,
                    String keyword,
                    Long categoryId,
                    String sortField,
                    String sortDir);
    
    Book getBookById(Long id);

    void saveBook(Book book);

    void deleteBook(Long id);
    
    DashboardStats getDashboardStats();
    
    List<BookDTO> getAllBooksAsDTO();
    
    PagedResponse<BookDTO> getBooksPaged(int page, int size);
    
    BookDTO getBookDTOById(Long id);
    
    BookDTO createBookFromDTO(BookDTO dto);
    
    BookDTO createBookWithImage(BookDTO dto, MultipartFile image);
    
    void saveBookWithImage(Book book, MultipartFile image);
}
