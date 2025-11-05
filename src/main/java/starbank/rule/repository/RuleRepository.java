package starbank.rule.repository;

import org.springframework.stereotype.Repository;
import starbank.rule.model.DynamicRule;

import java.util.UUID;

@Repository
public interface RuleRepository extends JpaRepository<DynamicRule, UUID> {
    void deleteByProductId(String productId);
}