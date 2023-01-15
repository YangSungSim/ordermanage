package com.github.prgrms.orders.Order;

import java.time.LocalDateTime;
import java.util.Optional;

import com.github.prgrms.orders.Order.State;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import static com.google.common.base.Preconditions.checkArgument;
import static org.apache.commons.lang3.StringUtils.isEmpty;
import static java.time.LocalDateTime.now;
import static org.apache.commons.lang3.ObjectUtils.defaultIfNull;

import java.util.Objects;

import static java.util.Optional.ofNullable;

public class Order {
    
    private final Long seq;

    private int userSeq;

    private int productSeq;

    private int reviewSeq;

    private State state;

    private String requestMsg;

    private String rejectMsg;
    
    private LocalDateTime completedAt;

    private LocalDateTime rejectedAt;

    private LocalDateTime createAt;

    public Long getSeq() {
        return seq;
    }

    public int getUserSeq() {
        return userSeq;
    }

    public int getProductSeq() {
        return productSeq;
    }

    public int getReviewSeq() {
        return reviewSeq;
    }

    public State getState() {
		return state;
	}

    public String getRequestMsg() {
        return requestMsg;
    }

    public String getRejectMsg() {
        return rejectMsg;
    }

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }

    public LocalDateTime getRejectedAt() {
        return rejectedAt;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public Order(int userSeq, int productSeq, int reviewSeq) {
        this(null, userSeq, productSeq, reviewSeq, State.REQUESTED, null, null, null,null, null);
    }


    public Order(Long seq, int userSeq, int productSeq, int reviewSeq, State state, String requestMsg, String rejectMsg, LocalDateTime completedAt, LocalDateTime rejectedAt, LocalDateTime createAt ) {

        checkArgument(
            isEmpty(requestMsg) || requestMsg.length() <= 1000,
            "requestMsg length must be less than 1000 characters"
        );
        checkArgument(
          isEmpty(rejectMsg) || rejectMsg.length() <= 1000,
          "rejectMsg length must be less than 1000 characters"
        );
        
        this.seq = seq;
        this.userSeq = userSeq;
        this.productSeq = productSeq;
        this.state = state;
        this.reviewSeq = reviewSeq;
        this.requestMsg = requestMsg;
        this.rejectMsg = rejectMsg;
        this.completedAt = completedAt;
        this.rejectedAt = rejectedAt;
        this.createAt = defaultIfNull(createAt, now());

    }

    public void setUserseq(int userSeq) {
        this.userSeq = userSeq;
    }

    public void setProductSeq(int productSeq) {
        this.productSeq = productSeq;
    }

    public void setReviewSeq(int reviewSeq) {
        this.reviewSeq = reviewSeq;
    }

    public void setRequestMsg(String requestMsg) {
        checkArgument(
            isEmpty(requestMsg) || requestMsg.length() <= 1000,
            "requestMsg length must be less than 1000 characters"
        );
        
        this.requestMsg = requestMsg;
    }

    public void setRejectMsg(String rejectMsg) {
        checkArgument(
            isEmpty(rejectMsg) || rejectMsg.length() <= 1000,
            "rejectMsg length must be less than 1000 characters"
        );
        
        this.rejectMsg = rejectMsg;
    }

    // public void setCompletedAt(LocalDateTime completedAt) {
    //     this.completedAt = completedAt;
    // }

    // public void setRejectedAt(LocalDateTime rejectedAt) {
    //     this.rejectedAt = rejectedAt;
    // }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(seq, order.seq);
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
        .append("reviewSeq", reviewSeq)
        .append("state", state)
        .append("requestMsg", requestMsg)
        .append("rejectMsg", rejectMsg)
        .append("completedAt", completedAt)
        .append("rejectedAt", rejectedAt)
        .append("createAt", createAt)
        .toString();
    }

    
  static public class Builder {
    private Long seq;
    private int userSeq;
    private int productSeq;
    private int reviewSeq;
    private State state;
    private String requestMsg;
    private String rejectMsg;
    private LocalDateTime rejectedAt;
    private LocalDateTime completedAt;
    private LocalDateTime createAt;

    public Builder() {/*empty*/}

    public Builder(Order order) {
      this.seq = order.seq;
      this.userSeq = order.userSeq;
      this.productSeq = order.productSeq;
      this.reviewSeq = order.reviewSeq;
      this.state = order.state;
      this.requestMsg = order.requestMsg;
      this.rejectMsg = order.rejectMsg;
      this.completedAt = order.completedAt;
      this.rejectedAt = order.rejectedAt;
      this.createAt = order.createAt;
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

    public Builder reviewSeq(int reviewSeq) {
        this.reviewSeq = reviewSeq;
        return this;
    }

    public Builder State(State state) {
        this.state = state;
        return this;
    }

    public Builder requestMsg(String requestMsg) {
      this.requestMsg = requestMsg;
      return this;
    }

    public Builder rejectMsg(String rejectMsg) {
      this.rejectMsg = rejectMsg;
      return this;
    }

    public Builder completedAt(LocalDateTime completedAt) {
        this.completedAt = completedAt;
        return this;
    } 

    public Builder rejectedAt(LocalDateTime rejectedAt) {
        this.rejectedAt = rejectedAt;
        return this;
    }


    public Builder createAt(LocalDateTime createAt) {
      this.createAt = createAt;
      return this;
    }

    public Order build() {
      return new Order(seq,
      userSeq,
      productSeq,
      reviewSeq,
      state,
      requestMsg,
      rejectMsg,
      completedAt,
      rejectedAt,
      createAt);
    }


  }
}
