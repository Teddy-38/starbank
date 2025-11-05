package starbank.controller;

import starbank.dao.ProductDao;
import starbank.dao.UserDao;
import starbank.model.BankProduct;
import starbank.model.Transaction;
import starbank.model.User;
import starbank.rules.RecommendationRule;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class RecommendationService {
    public static List<BankProduct> getRecommendations(long userId) {

        @Service
        class recommendationService {

            private final UserDao userDao;
            private final Transaction transactionDao;
            private final ProductDao productDao;
            private final Map<Long, List<RecommendationRule>> rulesByProductId;

            public recommendationService(UserDao userDao, Transaction transactionDao, ProductDao productDao, List<RecommendationRule> allRules) {
                this.userDao = userDao;
                this.transactionDao = transactionDao;
                this.productDao = productDao;
                this.rulesByProductId = allRules.stream()
                        .collect(Collectors.groupingBy(RecommendationRule::getAssociatedProductId));
            }

            public List<BankProduct> getRecommendations(long userId) {
                User user = userDao.findById(userId)
                        .orElseThrow();
                return List.of();
            }
        }
        return List.of();
    }
}