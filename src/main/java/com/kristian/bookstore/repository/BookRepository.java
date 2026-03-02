package com.kristian.bookstore.repository;

import com.kristian.bookstore.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    
    Page<Book> findByTitleContainingIgnoreCase(String title, Pageable pageable);
    
    Page<Book> findByCategoryId(Long categoryId, Pageable pageable);

    Page<Book> findByTitleContainingIgnoreCaseAndCategoryId(
            String title,
            Long categoryId,
            Pageable pageable);
    
    boolean existsByTitleIgnoreCaseAndCategoryId(String title, Long categoryId);
    
    long countByQuantity(int quantity);

    @Query("SELECT SUM(b.quantity) FROM Book b")
    Long getTotalStock();

    Book findTopByOrderByPriceDesc();
}