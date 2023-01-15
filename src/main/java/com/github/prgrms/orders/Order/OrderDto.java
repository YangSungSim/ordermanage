package com.github.prgrms.orders.Order;

import java.time.LocalDateTime;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import static org.springframework.beans.BeanUtils.copyProperties;

public class OrderDto {
    private Long seq;

    private int userSeq;

    private int productSeq;

    private int reviewSeq;

    private State state;

    private String requestMsg;

    private String rejectMsg;
    
    private LocalDateTime completedAt;

    private LocalDateTime rejectedAt;

    private LocalDateTime createAt;

    public OrderDto(Order source) {
        copyProperties(source, this);
    
        this.requestMsg = source.getRequestMsg();
    }

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

    public void setSeq(Long seq) {
        this.seq = seq;
    }

    public void setUserSeq(int userSeq) {
        this.userSeq = userSeq;
    }

    public void setProductSeq(int productSeq) {
        this.productSeq = productSeq;
    }

    public void setReviewSeq(int reviewSeq) {
        this.reviewSeq = reviewSeq;
    }

    public void setRequestMsg(String requestMsg) {
        this.requestMsg = requestMsg;
    }

    public void setRejectMsg(String rejectMsg) {
        this.rejectMsg = rejectMsg;
    }

    public void setState(State state) {
		this.state = state;
	}

    public void setCompletedAt(LocalDateTime completedAt) {
        this.completedAt = completedAt;
    }

    public void setRejectedAt(LocalDateTime rejectedAt) {
        this.rejectedAt = rejectedAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
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
}
