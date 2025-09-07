package com.srivath.product.models;



import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.List;

@Getter
@Setter
@Entity
@JsonDeserialize
public class Category extends BaseModel{
    private String name;
//    @OneToMany(mappedBy = "category", fetch=FetchType.LAZY) // LAZY means it will not pull all Products while pulling a category record.
//    //@JsonIgnore
//    //@Field(type = FieldType.Nested) // This is used to indicate that the products field is a nested object in Elasticsearch.
//    @JsonManagedReference
//    private List<Product> products;
    public Category()
    {
        name = "";
    }
    public Category(String categoryName)
    {
        name = categoryName;
    }
}
