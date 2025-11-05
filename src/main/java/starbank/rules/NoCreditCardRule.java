package starbank.rules;

import org.springframework.stereotype.Component;
import starbank.model.Transaction;
import starbank.model.User;

import java.util.List;

@Component
public class NoCreditCardRule implements RecommendationRule {
    private static final long CREDIT_CARD_PRODUCT_ID = 202;

    @Override
    public boolean evaluate(User user, List<Transaction> userTransactions) {
        return userTransactions.stream()
                .noneMatch(t -> "CREDIT".equals(getProductTypeById(t.getProductId())));
    }

    @Override
    public long getAssociatedProductId() {
        return CREDIT_CARD_PRODUCT_ID;
    }
    private String getProductTypeById(long productId) {
        return null;
    }
}