package com.github.prgrms.orders.Order;

import java.time.LocalDateTime;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class RejectResult {

    private final Boolean result;

    public RejectResult(Boolean result) {
        this.result = result;
    }

    public Boolean getResult() {
        return result;
    }
    
    @Override
    public String toString() {
      return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
        .append(result)
        .toString();
    }
}
