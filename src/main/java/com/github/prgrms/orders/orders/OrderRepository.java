package com.github.prgrms.orders.Order;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.github.prgrms.configures.web.Pageable;

public interface OrderRepository {
    
    List<Order> findAll(Pageable simplePageRequest);

    Optional<Order> findById(long id);

    Optional<Order> findByProductUser(int productSeq,int userSeq);

    void updateReviewSeq(Long orderSeq,int productSeq2,int userSeq2,Long reviewSeq2,State orderState2,
    String orderRequestMsg,String orderRejectMsg,LocalDateTime COMPLETED_AT,LocalDateTime REJECTED_AT,LocalDateTime CREATE_AT);

    void updateState(long id);

    void updateReject(long id, String rejectMsg);

    void updateShipping(long id);

    void updateComplete(long id);
}
