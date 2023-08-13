package com.example.product.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

public class ProductDTO {
    private long id;
    private String name;
    private Integer calories;

    public ProductDTO() {
    }

    public ProductDTO(long id, String name, Integer calories) {
        this.id = id;
        this.name = name;
        this.calories = calories;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCalories() {
        return calories;
    }

    public void setCalories(Integer calories) {
        this.calories = calories;
    }
}
