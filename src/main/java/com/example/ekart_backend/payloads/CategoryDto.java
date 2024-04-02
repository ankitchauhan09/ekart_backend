package com.example.ekart_backend.payloads;

import com.example.ekart_backend.entities.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto {
    private Integer id;
    private String categoryName;
    private String categoryDescription;
//    private List<ProductDto> product = new ArrayList<>();

}
