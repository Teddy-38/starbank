package starbank.rule.service;

import starbank.model.Recommendation;
import starbank.repository.UserKnowledgeRepository;
import starbank.rule.engine.RuleEngine;
import starbank.rule.model.DynamicRule;
import starbank.rule.repository.RuleRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class RecommendationService {

    private static final Logger logger = LoggerFactory.getLogger(RecommendationService.class);

    private final UserKnowledgeRepository userKnowledgeRepository;
    private final RuleRepository ruleRepository; // Репозиторий для правил
    private final RuleEngine ruleEngine; // Движок правил

    public List<Recommendation> getRecommendations(int userId) {
        long startTime = System.currentTimeMillis();
        logger.info("Starting recommendation generation for user {}", userId);

        Set<Recommendation> recommendations = new HashSet<>();

        // 1. Старые, "жестко закодированные" правила
        addFixedRecommendations(userId, recommendations);

        // 2. Новые, динамические правила
        addDynamicRecommendations(userId, recommendations);

        long endTime = System.currentTimeMillis();
        logger.info("Finished recommendation generation for user {} in {} ms. Found {} recommendations.",
                userId, (endTime - startTime), recommendations.size());

        return new ArrayList<>(recommendations);
    }

    private void addFixedRecommendations(int userId, Set<Recommendation> recommendations) {
        // Простой кредит
        boolean hasCredit = userKnowledgeRepository.countUserTransactionsByProductType(userId, "CREDIT") > 0;
        double debitDeposits = userKnowledgeRepository.getTransactionSum(userId, "DEBIT", "DEPOSIT");
        double debitWithdraws = userKnowledgeRepository.getTransactionSum(userId, "DEBIT", "WITHDRAW");

        if (!hasCredit && debitDeposits > debitWithdraws && debitWithdraws > 100000) {
            recommendations.add(new Recommendation(
                    "Простой кредит",
                    "ab138afb-f3ba-4a93-b74f-0fcee86d447f",
                    "Мы заметили, что вы активно пользуетесь дебетовыми продуктами. Возможно, вам будет интересен наш 'Простой кредит' с выгодными условиями."
            ));
        }

    }

    private void addDynamicRecommendations(int userId, Set<Recommendation> recommendations) {
        List<DynamicRule> allRules = ruleRepository.findAll();
        logger.debug("Found {} dynamic rules to check.", allRules.size());

        for (DynamicRule rule : allRules) {
            if (ruleEngine.evaluate(rule, userId)) {
                logger.debug("Rule ID {} matched for user {}. Recommending product '{}'", rule.getId(), userId, rule.getProductName());
                recommendations.add(new Recommendation(
                        rule.getProductName(),
                        rule.getProductId(),
                        rule.getProductText()
                ));
            }
        }
    }
}