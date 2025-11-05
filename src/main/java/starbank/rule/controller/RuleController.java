package starbank.rule.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import starbank.rule.dto.CreateRuleRequest;
import starbank.rule.dto.RuleDto;
import starbank.rule.service.RuleService;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rule")
@RequiredArgsConstructor
public class RuleController {

    private final RuleService ruleService;

    @PostMapping
    public ResponseEntity<RuleDto> createRule(@RequestBody CreateRuleRequest request) {
        RuleDto createdRule = ruleService.createRule(request);
        return ResponseEntity.ok(createdRule);
    }

    @GetMapping
    public ResponseEntity<Map<String, List<RuleDto>>> getAllRules() {
        List<RuleDto> rules = ruleService.getAllRules();
        return ResponseEntity.ok(Collections.singletonMap("data", rules));
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteRule(@PathVariable String productId) {
        ruleService.deleteRule(productId);
        return ResponseEntity.noContent().build();
    }
}