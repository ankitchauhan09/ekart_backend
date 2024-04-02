package com.example.ekart_backend.controller;

import com.example.ekart_backend.exceptions.ApiResponse;
import com.example.ekart_backend.payloads.ReviewsDto;
import com.example.ekart_backend.repositories.ReviewsRepo;
import com.example.ekart_backend.services.serviceInterface.ReviewsService;
import org.apache.coyote.Response;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/review")
public class ReviewsController {

    @Autowired
    private ReviewsService reviewsService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping("/product/{productId}")
    public ResponseEntity<?> createReview(@PathVariable("productId") Integer productId, @RequestBody ReviewsDto reviewsDto){
        try{
            return new ResponseEntity<>(this.reviewsService.createReview(reviewsDto, productId), HttpStatus.CREATED);
        }
        catch (Exception e){
            return new ResponseEntity<>(new ApiResponse(e.getMessage(), false), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<?> getReviews(@PathVariable("productId") Integer productId){
        try{
            return new ResponseEntity<>(this.reviewsService.getAllReviews(productId), HttpStatus.FOUND);
        }
        catch (Exception e){
            return new ResponseEntity<>(new ApiResponse(e.getMessage(), false), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{reviewId}/product/{productId}")
    public ResponseEntity<?> deleteReviewById(@PathVariable("productId") Integer productId, @PathVariable("reviewId") Integer reviewId){
        try{
            this.reviewsService.deleteReview(reviewId);
            return new ResponseEntity<>(new ApiResponse("Review Deleted Successfully", true), HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(new ApiResponse(e.getMessage(), false), HttpStatus.BAD_REQUEST);
        }
    }

}
