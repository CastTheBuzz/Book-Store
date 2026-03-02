package com.kristian.bookstore.service;

public class BookDeletionException extends RuntimeException {

    public BookDeletionException(String message) {
        super(message);
    }
}