package com.github.prgrms.orders.Order;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import com.github.prgrms.configures.web.Pageable;

import static java.time.LocalDateTime.now;
import static com.github.prgrms.utils.DateTimeUtils.dateTimeOf;
import static java.util.Optional.ofNullable;

import java.time.LocalDateTime;

@Repository
public class JdbcOrderRepository implements OrderRepository {
    
    private final JdbcTemplate jdbcTemplate;

    public JdbcOrderRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Order> findAll(Pageable simplePageRequest) {

      long offset = simplePageRequest.getOffset();
      int size = simplePageRequest.getSize();

      return jdbcTemplate.query(
        "SELECT * FROM orders ORDER BY seq DESC LIMIT ? OFFSET ?",
        mapper,
        size,
        offset
      );
    }

    @Override
    public Optional<Order> findById(long id) {

      List<Order> results = jdbcTemplate.query(
        "SELECT * FROM orders WHERE seq=?",
        mapper,
        id
      );

      return ofNullable(results.isEmpty() ? null : results.get(0));
    }

    @Override
    public Optional<Order> findByProductUser(int productSeq,int userSeq) {

      List<Order> results = jdbcTemplate.query(
        "SELECT * FROM orders WHERE product_seq= ? and user_seq= ? and state='COMPLETED'",
        mapper,
        productSeq,
        userSeq
      );

      return ofNullable(results.isEmpty() ? null : results.get(0));
    }

    @Override
    public void updateReviewSeq(Long orderSeq,int productSeq2,int userSeq2,Long reviewSeq2,State orderState2,
    String orderRequestMsg,String orderRejectMsg,LocalDateTime COMPLETED_AT,LocalDateTime REJECTED_AT,LocalDateTime CREATE_AT) {

      jdbcTemplate.update(
        "DELETE FROM orders WHERE product_seq= ? and user_seq= ? and state='COMPLETED'",
        productSeq2,
        userSeq2
      );

      jdbcTemplate.update(
        "INSERT INTO ORDERS (SEQ, USER_SEQ, PRODUCT_SEQ, REVIEW_SEQ, STATE, REQUEST_MSG,REJECT_MSG, "
        + "COMPLETED_AT, REJECTED_AT, CREATE_AT) VALUES (? , ? , ? , ?, ?, ?, ?, ?, ?, ?)",
        orderSeq,
        userSeq2,  
        productSeq2, 
        reviewSeq2, 
        orderState2.toString(), 
        orderRequestMsg, 
        orderRejectMsg, 
        COMPLETED_AT, 
        REJECTED_AT, 
        CREATE_AT
      );

    }

    @Override
    public void updateState(long id) {

      jdbcTemplate.update(
        "UPDATE orders SET state = 'ACCEPTED' WHERE seq=?",
        id
      );
    }

    @Override
    public void updateReject(long id, String rejectMsg) {

      jdbcTemplate.update(
        "UPDATE orders SET state = 'REJECTED', reject_msg=?, rejected_at=? WHERE seq=?",
        rejectMsg,
        now(),   //주문거절시각은 현재시각으로
        id
      );
    }

    @Override
    public void updateShipping(long id) {

      jdbcTemplate.update(
        "UPDATE orders SET state = 'SHIPPING' WHERE seq=?",
        id
      );
    }

    
    @Override
    public void updateComplete(long id) {

      jdbcTemplate.update(
        "UPDATE orders SET state = 'COMPLETED', completed_at =? WHERE seq=?",
        now(),
        id
      );
    }

    static RowMapper<Order> mapper = (rs, rowNum) ->
    new Order.Builder()
      .seq(rs.getLong("seq"))
      .userSeq(rs.getInt("user_seq"))
      .productSeq(rs.getInt("product_seq"))
      .reviewSeq(rs.getInt("review_seq"))
      .State(State.values()[rs.getInt("state")])
      .requestMsg(rs.getString("request_msg"))
      .rejectMsg(rs.getString("reject_msg"))
      .completedAt(dateTimeOf(rs.getTimestamp("completed_at")))
      .rejectedAt(dateTimeOf(rs.getTimestamp("rejected_at")))
      .createAt(dateTimeOf(rs.getTimestamp("create_at")))
      .build();
}
