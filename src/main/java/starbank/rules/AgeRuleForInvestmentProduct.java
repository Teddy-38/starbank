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
        // Просто вызываем метод getAge() без аргументов
        int age = user.getAge();

        // Рекомендуем, если пользователю 18 лет или больше
        return age >= 18;
    }

    @Override
    public long getAssociatedProductId() {
        return INVESTMENT_PRODUCT_ID;
    }
}