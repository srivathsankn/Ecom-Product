package com.srivath.product.models;



import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
public class Category extends BaseModel{
    private String name;
    @OneToMany(mappedBy = "category", fetch=FetchType.LAZY) // LAZY means it will not pull all Products while pulling a category record.
    @JsonIgnore
    private List<Product> products;
    public Category()
    {
        name = "";
    }
    public Category(String categoryName)
    {
        name = categoryName;
    }
}
