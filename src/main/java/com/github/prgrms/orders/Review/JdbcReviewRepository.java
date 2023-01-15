package com.github.prgrms.orders.Review;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.github.prgrms.utils.DateTimeUtils.dateTimeOf;
import static java.util.Optional.ofNullable;

@Repository
public class JdbcReviewRepository implements ReviewRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcReviewRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<Review> findById(long id) {

      List<Review> results = jdbcTemplate.query(
        "SELECT * FROM reviews WHERE seq=?",
        mapper,
        id
      );

      return ofNullable(results.isEmpty() ? null : results.get(0));
    }

    @Override
    public Optional<Review> findByProductSeq(int productSeq) {

      List<Review> results = jdbcTemplate.query(
        "SELECT * FROM reviews WHERE product_seq=?",
        mapper,
        productSeq
      );

      return ofNullable(results.isEmpty() ? null : results.get(0));
    }

    @Override
    public void insertByContent(int userSeq, int productSeq, String content) {
      jdbcTemplate.update(
        "INSERT INTO reviews (user_seq, product_seq, content) VALUES (?, ?, ?)",
        userSeq,
        productSeq,
        content
      );
    }


    static RowMapper<Review> mapper = (rs, rowNum) ->
    new Review.Builder()
      .seq(rs.getLong("seq"))
      .userSeq(rs.getInt("user_seq"))
      .productSeq(rs.getInt("product_seq"))
      .content(rs.getString("content"))
      .createAt(dateTimeOf(rs.getTimestamp("create_at")))
      .build();
    
}
