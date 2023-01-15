package com.github.prgrms.orders.Review;


import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.time.LocalDateTime;
import java.util.Objects;
import static java.util.Optional.ofNullable;

import java.util.Optional;

import static com.google.common.base.Preconditions.checkArgument;
import static java.time.LocalDateTime.now;
import static org.apache.commons.lang3.ObjectUtils.defaultIfNull;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;


public class Review {
    private final Long seq;

    private int userSeq;

    private int productSeq;

    private String content;

    private final LocalDateTime createAt;

    public Review(int userSeq, int productSeq) {
        this(null, userSeq, productSeq, null, null);
    }

    public Review(Long seq, int userSeq, int productSeq, String content, LocalDateTime createAt) {
        checkArgument(isNotEmpty(content), "content must be provided");
        
        this.seq = seq;
        this.userSeq = userSeq;
        this.productSeq = productSeq;
        this.content = content;
        this.createAt = defaultIfNull(createAt, now());
    }

    public void setUserSeq(int userSeq) {
        this.userSeq = userSeq;
    }

    public void setProductSeq(int productSeq) { 
        this.productSeq = productSeq;
    }

    public void setContent(String content) {
        checkArgument(isNotEmpty(content), "content must be provided");
        
        this.content = content;
    }

    public Long getSeq() {
        return seq;
    }

    public int getProductSeq() {
        return productSeq;
    }

    public int getUserSeq() {
        return userSeq;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Review review = (Review) o;
        return Objects.equals(seq, review.seq);
    }
  
    @Override
    public int hashCode() {
        return Objects.hash(seq);
    }
  
    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
        .append("seq", seq)
        .append("userSeq", userSeq)
        .append("productSeq", productSeq)
        .append("content", content)
        .append("createAt", createAt)
        .toString();
    }
  
  
    static public class Builder {
    private Long seq;
    private int userSeq;
    private int productSeq;
    private String content;
    private LocalDateTime createAt;

    public Builder() {/*empty*/}

    public Builder(Review review) {
      this.seq = review.seq;
      this.userSeq = review.userSeq;
      this.productSeq = review.productSeq;
      this.content = review.content;
      this.createAt = review.createAt;
    }

    public Builder seq(Long seq) {
      this.seq = seq;
      return this;
    }

    public Builder userSeq(int userSeq) {
      this.userSeq = userSeq;
      return this;
    }

    public Builder productSeq(int productSeq) {
      this.productSeq = productSeq;
      return this;
    }

    public Builder content(String content) {
      this.content = content;
      return this;
    }

    public Builder createAt(LocalDateTime createAt) {
      this.createAt = createAt;
      return this;
    }

    public Review build() {
      return new Review(
		    seq,
        userSeq,
        productSeq,
        content,
        createAt
      );
    }
  }

  public Optional<String> getContent() {
    return ofNullable(content);
  }
}
