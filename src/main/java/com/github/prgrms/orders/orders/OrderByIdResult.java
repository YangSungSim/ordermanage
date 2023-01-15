package com.github.prgrms.orders.Order;

import java.time.LocalDateTime;
import java.util.Optional;

import com.github.prgrms.orders.Review.Review;
import com.github.prgrms.orders.Review.ReviewResultDto;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class OrderByIdResult {
    private final Long seq;

    private final int productId;

    private final State state;

    private final ReviewResultDto review;

    private final Optional<String> requestMessage;

    private final Optional<String> rejectMessage;

    private final LocalDateTime completedAt;

    private final LocalDateTime rejectedAt;

    private final LocalDateTime createAt;

    
  public OrderByIdResult(Long seq, int productId, Optional<Review> review2, State state, Optional<String> requestMessage,Optional<String> rejectMessage,LocalDateTime completedAt, LocalDateTime rejectedAt, LocalDateTime createAt) {
    this.seq = seq;
    this.productId = productId;

    if (review2 != null) {
      Optional<String> cadena = review2.get().getContent();
      String reviewss = String.valueOf(cadena.get());
      this.review = new ReviewResultDto(review2.get().getSeq(), review2.get().getProductSeq(), reviewss, review2.get().getCreateAt());
    } else {
      this.review = null;
    }

    this.state = state;
    this.requestMessage = requestMessage;
    this.rejectMessage = rejectMessage;
    this.completedAt = completedAt;
    this.rejectedAt = rejectedAt;
    this.createAt = createAt;
  }

  public Long getSeq() {
    return this.seq;
  }

  public int getProductId() {
    return this.productId;
  }

  public ReviewResultDto getReview() {
    return this.review;
  }

  public State getState() {
    return this.state;
  }

  public Optional<String> getRequestMessage() {
    return this.requestMessage;
  }

  public Optional<String> getRejectMessage() {
    return this.rejectMessage;
  }

  public LocalDateTime getCompletedAt() {
    return this.completedAt;
  }
  
  public LocalDateTime getRejectedAt() {
    return this.rejectedAt;
  }
  
  public LocalDateTime getCreateAt() {
    return this.createAt;
  }


  @Override
  public String toString() {
    return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
      .append("seq", getSeq())
      .append("productId", getProductId())
      .append("review", getReview())
      .append("state", getState())
      .append("requestMessage", getRequestMessage())
      .append("rejectMessage", getRejectMessage())
      .append("completedAt", getCompletedAt())
      .append("rejectedAt", getRejectedAt())
      .append("createAt", getCreateAt())
      .toString();
  }
}
