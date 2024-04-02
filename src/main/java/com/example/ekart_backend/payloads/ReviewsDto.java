package com.example.ekart_backend.payloads;

import com.example.ekart_backend.entities.Product;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewsDto {

    private Integer registerId;
    private String reviewContent;
}
