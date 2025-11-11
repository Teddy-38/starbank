package starbank.rule.repository;

import jdk.jfr.Enabled;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@starbank.rule.repository.RequiredArgsConstructor {
    private final JdbcTemplate jdbcTemplate;

    @Enabled
    public int countUserTransactionsByProductType(int userId, String productType) {
        String sql = "SELECT count(*) FROM transactions t JOIN products p ON t.product_id = p.id WHERE t.user_id = ? AND p.type = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, userId, productType);
        return count != null ? count : 0;
    }

    @Cacheable("userTransactionSum")
    public double getTransactionSum(int userId, String productType, String transactionType) {
        String sql = "SELECT sum(t.amount) FROM transactions t JOIN products p ON t.product_id = p.id WHERE t.user_id = ? AND p.type = ? AND t.type = ?";
        Double sum = jdbcTemplate.queryForObject(sql, Double.class, userId, productType, transactionType);
        return sum != null ? sum : 0.0;
    }
}