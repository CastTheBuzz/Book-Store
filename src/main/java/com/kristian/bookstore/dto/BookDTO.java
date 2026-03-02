package com.kristian.bookstore.dto;

import jakarta.validation.constraints.*;

public class BookDTO {

    private Long id;
    
    @NotBlank(message = "Title is required")
    private String title;
    
    @NotBlank(message = "Author is required")
    private String author;
    
    @NotNull(message = "Price is required")
    @Positive(message = "Price must be positive")
    private Double price;
    
    @NotNull(message = "Quantity is required")
    @Min(value = 0, message = "Quantity cannot be negative")
    private Integer quantity;
    
    @NotBlank(message = "Description is required")
    private String description;

    private String imagePath;
    
    @NotBlank(message = "Category is required")
    private String categoryName;
    
    
    public BookDTO() {}

    public BookDTO(Long id, String title, String author,
                   Double price, Integer quantity,
                   String categoryName,
                   String description,
                   String imagePath) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.price = price;
        this.quantity = quantity;
        this.categoryName = categoryName;
        this.description = description;
        this.imagePath = imagePath;
    }

    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public Double getPrice() { return price; }
    public Integer getQuantity() { return quantity; }
    public String getCategoryName() { return categoryName; }
    public String getDescription() { return description; }
    public String getImagePath() { return imagePath; }
    
    public void setId(Long id) { this.id = id; }
    public void setTitle(String title) { this.title = title; }
    public void setAuthor(String author) { this.author = author; }
    public void setPrice(Double price) { this.price = price; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
    public void setCategoryName(String categoryName) { this.categoryName = categoryName; }
    public void setDescription(String description) { this.description = description; }
    public void setImagePath(String imagePath) { this.imagePath = imagePath; }

}
