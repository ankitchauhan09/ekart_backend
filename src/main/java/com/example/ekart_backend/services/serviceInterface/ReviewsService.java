package com.example.ekart_backend.services.serviceInterface;

import com.example.ekart_backend.payloads.ReviewsDto;

import java.util.List;

public interface ReviewsService {

    public ReviewsDto createReview(ReviewsDto reviewsDto, Integer productId);
    public void deleteReview(ReviewsDto reviewsDto);
    public void deleteReview(Integer reviewId);
    public List<ReviewsDto> getAllReviews(Integer productId);
}
