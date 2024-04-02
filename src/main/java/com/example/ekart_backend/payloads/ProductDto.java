package com.example.ekart_backend.payloads;

import com.example.ekart_backend.entities.Category;
import com.example.ekart_backend.entities.Reviews;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {

    private Integer id;
    private String productName;
    private String productDescription;
    private Integer productCost;
    private Integer productDiscount;
    private Integer productQuantity;
    private List<ReviewsDto> reviews = new ArrayList<>();
    private List<String> productImage = new ArrayList<>();
    private CategoryDto category;
    private List<ImageResponse> images = new ArrayList<>();
}
