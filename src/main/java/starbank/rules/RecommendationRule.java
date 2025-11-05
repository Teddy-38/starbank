package starbank.rules;


import starbank.model.Transaction;
import starbank.model.User;

import java.util.List;

public interface RecommendationRule {
    /**
     * Проверяет, применимо ли правило к пользователю и его транзакциям.
     * @return true, если продукт можно рекомендовать по этому правилу.
     */
    boolean evaluate(User user, List<Transaction> userTransactions);

    /**
     * Возвращает ID продукта, к которому привязано это правило.
     */
    long getAssociatedProductId();
}
