package com.github.prgrms.orders.Order;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.github.prgrms.configures.web.Pageable;

import static com.google.common.base.Preconditions.checkNotNull;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Transactional(readOnly = true)
    public List<Order> findAll(Pageable simplePageRequest) {
      return orderRepository.findAll(simplePageRequest);
    }

    @Transactional(readOnly = true)
    public Optional<Order> findById(Long orderId) {
      checkNotNull(orderId, "orderId must be provided");

      return orderRepository.findById(orderId);
    }

    @Transactional(readOnly = true)
    public Optional<Order> findByProductUser(int productSeq,int userSeq) {
      checkNotNull(productSeq, "orderId must be provided");

      return orderRepository.findByProductUser(productSeq, userSeq);
    }

    //review seq order 테이블에 업데이트 시키키
    @Transactional(readOnly = true)
    public void updateReviewSeq(Long orderSeq,int productSeq2,int userSeq2,Long reviewSeq2,State orderState2,
    String orderRequestMsg,String orderRejectMsg,LocalDateTime COMPLETED_AT,LocalDateTime REJECTED_AT,LocalDateTime CREATE_AT) {
      checkNotNull(reviewSeq2, "reviewSeq must be provided");
      
      orderRepository.updateReviewSeq(orderSeq, productSeq2, userSeq2, reviewSeq2, orderState2, orderRequestMsg, orderRejectMsg, COMPLETED_AT, REJECTED_AT, CREATE_AT);
    }

    @Transactional(readOnly = true)
    public void updateState(Long orderId) {
      checkNotNull(orderId, "orderId must be provided");
      orderRepository.updateState(orderId);
    }

    @Transactional(readOnly = true)
    public void updateReject(Long orderId, String rejectMsg) {
      checkNotNull(orderId, "orderId must be provided");
      orderRepository.updateReject(orderId, rejectMsg);
    }

    @Transactional(readOnly = true)
    public void updateShipping(Long orderId) {
      checkNotNull(orderId, "orderId must be provided");
      orderRepository.updateShipping(orderId);
    }

    @Transactional(readOnly = true)
    public void updateComplete(Long orderId) {
      checkNotNull(orderId, "orderId must be provided");
      orderRepository.updateComplete(orderId);
    }

}