package starbank.dao;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import starbank.model.BankProduct;

import java.util.Optional;

@Repository
public class ProductDao {

    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<BankProduct> productRowMapper = (rs, rowNum) -> {
        BankProduct product = new BankProduct();
        product.setId(rs.getLong("id"));
        product.setProductName(rs.getString("product_name"));
        product.setProductType(rs.getString("product_type"));
        product.setDescription(rs.getString("description"));
        return product;
    };

    public ProductDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Optional<BankProduct> findById(long productId) {
        String sql = "SELECT * FROM products WHERE id = ?";
        try {
            BankProduct product = jdbcTemplate.queryForObject(sql, productRowMapper, productId);
            return Optional.ofNullable(product);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }
}