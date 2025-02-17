package com.srivath.product.models;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@JsonDeserialize
public class Product extends BaseModel {
    private String name;
    private String description;
    private Double price;
    private String image;
    @ManyToOne
    private Category category;
}
