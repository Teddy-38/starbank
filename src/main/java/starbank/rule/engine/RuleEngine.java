package starbank.rule.engine;

import starbank.repository.UserKnowledgeRepository;
import starbank.rule.model.DynamicRule;
import starbank.rule.model.QueryDefinition;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class RuleEngine {

    private static final Logger logger = LoggerFactory.getLogger(RuleEngine.class);
    private final UserKnowledgeRepository userKnowledgeRepository;

    public boolean evaluate(DynamicRule rule, int userId) {
        for (QueryDefinition queryDef : rule.getRule()) {
            boolean result = executeSingleQuery(queryDef, userId);
            if (!result) {
                // Если хоть один запрос не выполнился, все правило не выполняется
                return false;
            }
        }
        return true;
    }

    private boolean executeSingleQuery(QueryDefinition queryDef, int userId) {
        List<String> args = queryDef.getArguments();
        boolean queryResult;

        try {
            switch (queryDef.getQuery()) {
                case "USER_OF":
                    queryResult = userKnowledgeRepository.countUserTransactionsByProductType(userId, args.get(0)) > 0;
                    break;
                case "ACTIVE_USER_OF":
                    queryResult = userKnowledgeRepository.countUserTransactionsByProductType(userId, args.get(0)) >= 5;
                    break;
                case "TRANSACTION_SUM_COMPARE":
                    double sum = userKnowledgeRepository.getTransactionSum(userId, args.get(0), args.get(1));
                    double value = Double.parseDouble(args.get(3));
                    queryResult = compare(sum, value, args.get(2));
                    break;
                case "TRANSACTION_SUM_COMPARE_DEPOSIT_WITHDRAW":
                    double depositSum = userKnowledgeRepository.getTransactionSum(userId, args.get(0), "DEPOSIT");
                    double withdrawSum = userKnowledgeRepository.getTransactionSum(userId, args.get(0), "WITHDRAW");
                    queryResult = compare(depositSum, withdrawSum, args.get(1));
                    break;
                default:
                    logger.warn("Unknown query type: {}", queryDef.getQuery());
                    return false;
            }
        } catch (Exception e) {
            logger.error("Error executing query: {} for user: {}", queryDef, userId, e);
            return false;
        }

        return queryDef.isNegate() ? !queryResult : queryResult;
    }

    private boolean compare(double val1, double val2, String operator) {
        switch (operator) {
            case ">": return val1 > val2;
            case "<": return val1 < val2;
            case "=": return val1 == val2;
            case ">=": return val1 >= val2;
            case "<=": return val1 <= val2;
            default:
                throw new IllegalArgumentException("Unknown comparison operator: " + operator);
        }
    }
}