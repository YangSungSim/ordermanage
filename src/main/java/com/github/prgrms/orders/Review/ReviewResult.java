package com.github.prgrms.orders.Review;

import java.time.LocalDateTime;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;


public class ReviewResult {

  private final Long id;
  private final int productId;
  private final String content;
  private final LocalDateTime createAt;
    
  public ReviewResult(Long id, int productId, String content, LocalDateTime createAt) {
    this.id = id;
    this.productId = productId;
    this.content = content;
    this.createAt = createAt;
  }

  public Long getId() {
      return id;
  }

  public int getProductId() {
    return productId;
  }

  public String getContent() {
    return content;
  }

  public LocalDateTime getCreateAt() {
    return createAt;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
      .append("seq", id)
      .append("productId", productId)
      .append("content", content)
      .append("createAt", createAt)
      .toString();
  }

}
