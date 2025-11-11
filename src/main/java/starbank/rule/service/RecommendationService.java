package starbank.rule.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import starbank.model.User;
import starbank.rule.model.DynamicRule;
import starbank.rule.model.RuleStatistic;
import starbank.rule.repository.RuleStatisticRepository;

@Service
public class RecommendationService {

    private final RuleStatisticRepository ruleStatisticRepository;

    @Transactional
    public void applyDynamicRule(User user, DynamicRule rule) {
        boolean ruleMatched = ...;

        if (ruleMatched) {

            RuleStatistic statistic = rule.getStatistic();
            if (statistic == null) {
                statistic = new RuleStatistic(rule);
                rule.setStatistic(statistic);
            }
            statistic.setCount(statistic.getCount() + 1);
            ruleStatisticRepository.save(statistic);
        }
    }
}