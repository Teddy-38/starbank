package starbank.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class Transaction {
    private Long id;
    private Long userId;
    private Long productId;
    private BigDecimal amount;
    private LocalDateTime transactionDate;
    private String merchant;

    public long getProductId() {
        return 0;
    }

    public List<Transaction> findByUserId(long userId) {
        return List.of();
    }
}
