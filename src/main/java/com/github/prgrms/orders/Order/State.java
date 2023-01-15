package com.github.prgrms.orders.Order;

public enum State {

    REQUESTED( "REQUESTED" ),
	ACCEPTED( "ACCEPTED" ),
	SHIPPING( "SHIPPING" ),
	COMPLETED( "COMPLETED" ),
	REJECTED( "REJECTED" );

    private String orderStatus;
	
	State( String orderStatus ) {
		this.orderStatus = orderStatus;
	}
    
	public String getOrderStatus() {
		return this.orderStatus;
	}
}
