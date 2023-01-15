package com.github.prgrms.orders.Order;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.validation.constraints.NotBlank;

public class RejectRequest {
    
    @NotBlank(message = "message must be provided")
    private String message;

    protected RejectRequest() {/*empty*/}

    public RejectRequest(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
      return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
        .append("message", message)
        .toString();
    }
}
