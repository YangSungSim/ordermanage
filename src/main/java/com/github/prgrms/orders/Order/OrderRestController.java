package com.github.prgrms.orders.Order;

import static com.github.prgrms.utils.ApiUtils.success;
import static java.util.stream.Collectors.toList;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import com.github.prgrms.configures.web.Pageable;
import com.github.prgrms.errors.UnauthorizedException;
import com.github.prgrms.orders.Review.Review;
import com.github.prgrms.orders.Review.ReviewService;
import com.github.prgrms.utils.ApiUtils.ApiResult;
import com.github.prgrms.errors.GeneralExceptionHandler;
import com.github.prgrms.errors.NotFoundException;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/orders")
public class OrderRestController {

  private final OrderService orderService;

  private final ReviewService reviewService;

  public OrderRestController(OrderService orderService, ReviewService reviewService) {
    this.orderService = orderService;
    this.reviewService = reviewService;
  }

  // TODO findAll, findById, accept, reject, shipping, complete 메소드 구현이 필요합니다.

  /*
  *  findAll 구현
  *
  */
  @GetMapping
  public ApiResult<List<OrderByIdResult>> findAll( 
  Pageable simplePageRequest) {

    List<OrderDto> orderFindAllList = orderService.findAll(simplePageRequest).stream()
                .map(OrderDto::new)
                .collect(toList());

    int rowCount = orderFindAllList.size();   

    List<OrderByIdResult> resultFindAll =  new ArrayList<>();

    System.out.println("orderFindAllList:>>>>>>>>>>");
    System.out.println(orderFindAllList);
    
    for (int i =0; i< rowCount; i++) {

      OrderDto order = orderFindAllList.get(i);
      
      Long seq = order.getSeq();
      int productSeq = order.getProductSeq();
      int reviewSeq = order.getReviewSeq();
      long reviewSeq2 = Long.valueOf(reviewSeq);

      Optional<Review> review = null;
      if (reviewSeq2 > 0) {
        review = reviewService.findById(reviewSeq2);
      } else {
        review = null;
      }
      
      State state = order.getState();
      String requestMsg = order.getRequestMsg();
      Optional<String> requestMsg2 = Optional.ofNullable(requestMsg);
      String rejectMsg = order.getRejectMsg();
      Optional<String> rejectMsg2 = Optional.ofNullable(rejectMsg);
      LocalDateTime completedAt = order.getCompletedAt();
      LocalDateTime rejectedAt = order.getRejectedAt();
      LocalDateTime createAt = order.getCompletedAt();

      resultFindAll.add(new OrderByIdResult(seq, productSeq, review, state, requestMsg2, rejectMsg2, completedAt, rejectedAt, createAt) );
    }

      return success(resultFindAll);
  }

  /*
  *  findById 구현
  *
  */
  @GetMapping(path = "{id}")
  public ApiResult<OrderByIdResult> findById(@PathVariable Long id) {
    
    Optional<Order> order = orderService.findById(id);
    Long seq = order.get().getSeq();
    int productSeq = order.get().getProductSeq();
    int reviewSeq = order.get().getReviewSeq();
    long reviewSeq2 = Long.valueOf(reviewSeq);
    System.out.println("reviewSeq2: >>>>>>");
    System.out.println(reviewSeq2);

    Optional<Review> review = null;
    if (reviewSeq2 > 0) {
      review = reviewService.findById(reviewSeq2);
    } else {
      review = null;
    }
    
    State state = order.get().getState();
    String requestMsg = order.get().getRequestMsg();
    String rejectMsg = order.get().getRejectMsg();
    Optional<String> requestMsg2 = Optional.ofNullable(requestMsg);
    Optional<String> rejectMsg2 = Optional.ofNullable(rejectMsg);
    LocalDateTime completedAt = order.get().getCompletedAt();
    LocalDateTime rejectedAt = order.get().getRejectedAt();
    LocalDateTime createAt = order.get().getCompletedAt();

    return success(new OrderByIdResult(seq, productSeq, review, state, requestMsg2, rejectMsg2, completedAt, rejectedAt, createAt));
  }
  
  /*
  *  ACCEPT 구현
  *
  */
  @PatchMapping(path = "{id}/accept")
  public ApiResult<Boolean> accept(
    @PathVariable Long id
  ) throws UnauthorizedException {
    
    // orderstate 이 requested인지 확인 하기
    State orderState = orderService.findById(id).get().getState();

    if (orderState == State.REQUESTED) {
      orderService.updateState(id);  // REQUESTED -> ACCEPTED 로 변경
      return success(true);
    } else {
      return success(false);
    }
  }

  /*
  *  REJECT 구현
  *
  */
  @PatchMapping(path = "{id}/reject")
  public ApiResult<Boolean> reject(
    @PathVariable Long id,
    @Valid @RequestBody RejectRequest request
  ) {

      // orderstate 이 requested인지 확인 하기
      State orderState = orderService.findById(id).get().getState();
      String rejectMsg = request.getMessage();

      if (orderState == State.REQUESTED) {
        orderService.updateReject(id, rejectMsg);  // REQUESTED -> REJECTED 로 변경
        return success(true);
      } else {
        return success(false);
      }
    
  }

  /*
  *  SHIPPING 구현
  *
  */
  @PatchMapping(path = "{id}/shipping")
  public ApiResult<Boolean> shipping(
    @PathVariable Long id
  ) throws UnauthorizedException {
    
    // orderstate 이 requested인지 확인 하기
    State orderState = orderService.findById(id).get().getState();

    if (orderState == State.ACCEPTED) {
      orderService.updateShipping(id);  // ACCEPTED -> SHIPPING 로 변경
      return success(true);
    } else {
      return success(false);
    }
  }

  
  /*
  *  COMPLETED 구현
  *
  */
  @PatchMapping(path = "{id}/complete")
  public ApiResult<Boolean> complete(
    @PathVariable Long id
  ) throws UnauthorizedException {
    
    // orderstate 이 requested인지 확인 하기
    State orderState = orderService.findById(id).get().getState();

    if (orderState == State.SHIPPING) {
      orderService.updateComplete(id);  // SHIPPING -> COMPLETED 로 변경
      return success(true);
    } else {
      return success(false);
    }
  }
  
}