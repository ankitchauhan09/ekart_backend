package com.example.ekart_backend.services.servicesImpl;

import com.example.ekart_backend.entities.Cart;
import com.example.ekart_backend.entities.Product;
import com.example.ekart_backend.entities.Reviews;
import com.example.ekart_backend.payloads.ProductDto;
import com.example.ekart_backend.payloads.ReviewsDto;
import com.example.ekart_backend.repositories.ProductRepo;
import com.example.ekart_backend.repositories.ReviewsRepo;
import com.example.ekart_backend.services.serviceInterface.ReviewsService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewServiceImpl implements ReviewsService {

    @Autowired
    private ReviewsRepo reviewsRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ProductRepo productRepo;

    @Override
    public ReviewsDto createReview(ReviewsDto reviewsDto, Integer productId) {
        Reviews reviews = this.modelMapper.map(reviewsDto, Reviews.class);
        reviews.setProduct(this.productRepo.findById(productId).get());
        Reviews createdReview = this.reviewsRepo.save(reviews);
        return this.modelMapper.map(createdReview, ReviewsDto.class);
    }

    @Override
    public void deleteReview(ReviewsDto reviewsDto) {
        this.reviewsRepo.delete(this.modelMapper.map(reviewsDto, Reviews.class));
    }

    @Override
    public void deleteReview(Integer reviewId) {
        Reviews reviews = this.reviewsRepo.findById(reviewId).get();
        this.reviewsRepo.delete(reviews);
    }

    @Override
    public List<ReviewsDto> getAllReviews(Integer productId) {
        List<Reviews> reviewsList = this.reviewsRepo.findAllByProduct(this.productRepo.findById(productId).get());
        return reviewsList.stream().map(reviews -> this.modelMapper.map(reviews, ReviewsDto.class)).collect(Collectors.toList());
    }

}
