package starbank.rule.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import starbank.rule.dto.CreateRuleRequest;
import starbank.rule.dto.RuleDto;
import starbank.rule.model.DynamicRule;
import starbank.rule.repository.RuleRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RuleService {

    private final RuleRepository ruleRepository;

    @Transactional("ruleTransactionManager")
    public RuleDto createRule(CreateRuleRequest request) {
        DynamicRule rule = new DynamicRule();
        rule.setProductName(request.getProductName());
        rule.setProductId(request.getProductId());
        rule.setProductText(request.getProductText());
        rule.setRule(request.getRule());

        DynamicRule savedRule = ruleRepository.save(rule);
        return RuleDto.fromEntity(savedRule);
    }

    @Transactional(readOnly = true, transactionManager = "ruleTransactionManager")
    public List<RuleDto> getAllRules() {
        return ruleRepository.findAll().stream()
                .map(RuleDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional("ruleTransactionManager")
    public void deleteRule(String productId) {
        ruleRepository.deleteByProductId(productId);
    }
}