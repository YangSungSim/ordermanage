package com.github.prgrms.orders.Review;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.google.common.base.Preconditions.checkNotNull;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;

    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    @Transactional(readOnly = true)
    public Optional<Review> findById(Long reviewId) {
      checkNotNull(reviewId, "reviewId must be provided");
    
      return reviewRepository.findById(reviewId);
    }

    @Transactional(readOnly = true)
    public Optional<Review> findByProductSeq(int productSeq) {
      checkNotNull(productSeq, "productSeq must be provided");
    
      return reviewRepository.findByProductSeq(productSeq);
    }

    @Transactional(readOnly = true)
    public void insertByContent(int userSeq,int productSeq,String content) {
      checkNotNull(content, "content must be provided");
      reviewRepository.insertByContent(userSeq, productSeq,content);
    }

}