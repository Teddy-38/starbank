package starbank.rules;


import org.springframework.stereotype.Component;
import starbank.model.Transaction;
import starbank.model.User;

import java.util.List;

@Component
public class AgeRuleForInvestmentProduct implements RecommendationRule {
    private static final long INVESTMENT_PRODUCT_ID = 301;

    @Override
    public boolean evaluate(User user, List<Transaction> userTransactions) {
        int age = user.getAge();
        return age >= 18;
    }

    @Override
    public long getAssociatedProductId() {
        return INVESTMENT_PRODUCT_ID;
    }
}