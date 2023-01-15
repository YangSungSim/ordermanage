package com.github.prgrms.orders.Review;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.time.LocalDateTime;

import static org.springframework.beans.BeanUtils.copyProperties;

public class ReviewResultDto {
    private Long seq;

    private int productId;

    private String content;

    private LocalDateTime createAt;

    
    public ReviewResultDto() {
    }

    public ReviewResultDto(Long id, int productId, String content, LocalDateTime createAt) {
        copyProperties(id, this);
        copyProperties(productId, this);
        copyProperties(content, this);
        copyProperties(createAt, this);
    
        this.seq = id;
        this.productId = productId;
        this.content = content;
        this.createAt = createAt;
    }

    public Long getSeq() {
        return seq;
    }

    public void setSeq(Long seq) {
        this.seq = seq;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }
    
    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }

    @Override
    public String toString() {
      return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
        .append("seq", seq)
        .append("productId", productId)
        .append("content", content)
        .append("createAt", createAt)
        .toString();
    }
}
