package com.example.bean;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Book {

    private Integer id;
    private String bookName;
    private BigDecimal price;
    private Integer stock;

    public boolean isUpdateDataValid() {
        return bookName != null && price != null && stock != null && id != null;
    }

}
