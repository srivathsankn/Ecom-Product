package com.srivath.product.DTOs;

import com.srivath.product.models.Category;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDTO {
    private String name;
    private String description;
    private Double price;
    private String image;
    private String category;
}
