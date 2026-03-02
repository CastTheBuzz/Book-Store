package com.kristian.bookstore.service;

import com.kristian.bookstore.model.Book;

public class DashboardStats {

    private long totalBooks;
    private long totalCategories;
    private long outOfStock;
    private long totalStock;
    private Book mostExpensiveBook;

    public DashboardStats(long totalBooks,
                          long totalCategories,
                          long outOfStock,
                          long totalStock,
                          Book mostExpensiveBook) {

        this.totalBooks = totalBooks;
        this.totalCategories = totalCategories;
        this.outOfStock = outOfStock;
        this.totalStock = totalStock;
        this.mostExpensiveBook = mostExpensiveBook;
    }

    public long getTotalBooks() { return totalBooks; }
    public long getTotalCategories() { return totalCategories; }
    public long getOutOfStock() { return outOfStock; }
    public long getTotalStock() { return totalStock; }
    public Book getMostExpensiveBook() { return mostExpensiveBook; }
}