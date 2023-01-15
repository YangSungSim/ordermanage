package com.github.prgrms.orders.Review;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import static com.github.prgrms.utils.ApiUtils.success;
import static com.github.prgrms.utils.ApiUtils.error;

import java.time.LocalDateTime;
import java.util.Optional;

import javax.validation.Valid;

import com.github.prgrms.errors.NotFoundException;
import com.github.prgrms.errors.UnauthorizedException;
import com.github.prgrms.orders.Order.OrderService;
import com.github.prgrms.orders.Order.State;
import com.github.prgrms.products.ProductService;
import com.github.prgrms.utils.ApiUtils.ApiResult;

import static com.github.prgrms.utils.ApiUtils.ApiResult;

@RestController
@RequestMapping("api/orders")
public class ReviewRestController {

  private final ReviewService reviewService;

  private final OrderService orderService;

  private final ProductService productService;

  public ReviewRestController(ReviewService reviewService,
                                    OrderService orderService, 
                                    ProductService productService) {
    this.reviewService = reviewService;
    this.orderService = orderService;
    this.productService = productService;
  }

  // TODO review 메소드 구현이 필요합니다.

  @PostMapping(path = "{id}/review")
  public ApiResult<ReviewResult> review(
    @Valid @RequestBody ReviewRequest request,
    @PathVariable Long id
  ) throws UnauthorizedException {

    //{id}로  order state 가져오기
    //completed 인지 여부 확인 아닐시 에러 내기
    State orderState = orderService.findById(id).get().getState();
    int userSeq = orderService.findById(id).get().getUserSeq();
    int productSeq = orderService.findById(id).get().getProductSeq();

    //400에러
    if (orderState != State.COMPLETED) {
      return (ApiResult<ReviewResult>) error("not complete", HttpStatus.BAD_REQUEST);    
    }

    // 이미 작성한 리뷰인지 확인
    int reviewSeq = orderService.findById(id).get().getReviewSeq();

    //400에러
    if (reviewSeq > 0) {
      return (ApiResult<ReviewResult>) error("reivew already written", HttpStatus.BAD_REQUEST);
    }

    reviewService.insertByContent(userSeq, productSeq, request.getContent());
    int productSeq2 = reviewService.findByProductSeq(productSeq).get().getProductSeq();
    int userSeq2 = reviewService.findByProductSeq(productSeq).get().getUserSeq();
    LocalDateTime createAt = reviewService.findByProductSeq(productSeq).get().getCreateAt();

    Long reviewSeq2 = reviewService.findByProductSeq(productSeq).get().getSeq();

    // product 테이블에 reviewcount +1 시키기
    productService.reviewUp(productSeq2);

    // order 테이블에 reviewSeq update 시키기
    System.out.println("orderService.findByProductUser: >>>>>>>>>>");
    System.out.println(orderService.findByProductUser(productSeq, userSeq));

    Long orderSeq = orderService.findByProductUser(productSeq, userSeq).get().getSeq();
    State orderState2 = orderService.findByProductUser(productSeq, userSeq).get().getState();
    String orderRequestMsg = orderService.findByProductUser(productSeq, userSeq).get().getRequestMsg();
    String orderRejectMsg = orderService.findByProductUser(productSeq, userSeq).get().getRequestMsg();
    LocalDateTime COMPLETED_AT = orderService.findByProductUser(productSeq, userSeq).get().getCompletedAt();
    LocalDateTime REJECTED_AT = orderService.findByProductUser(productSeq, userSeq).get().getRejectedAt();
    LocalDateTime CREATE_AT = orderService.findByProductUser(productSeq, userSeq).get().getCreateAt();

    orderService.updateReviewSeq(orderSeq, productSeq2, userSeq2, reviewSeq2, orderState2, orderRequestMsg, orderRejectMsg, COMPLETED_AT, REJECTED_AT, CREATE_AT);
    
    return success(new ReviewResult(id, productSeq2, request.getContent(), createAt));
  }


  @GetMapping(path = "{id}/review")
  public ApiResult<ReviewDto> findById(@PathVariable Long id) {

    return success(reviewService.findById(id)
      .map(ReviewDto::new)
      .orElseThrow(() -> new NotFoundException("Could not write review for order " + id)));
  }
}