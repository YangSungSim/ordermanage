package com.github.prgrms.orders.Review;

import java.util.Optional;

public interface ReviewRepository {

    Optional<Review> findById(long id);

    Optional<Review> findByProductSeq(int productSeq);

    void insertByContent(int userSeq, int productSeq, String content);
    
}
