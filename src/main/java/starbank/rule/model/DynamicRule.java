package starbank.rule.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import starbank.rule.converter.RuleJsonConverter;

import java.util.List;
import java.util.UUID;

@Entity
public class DynamicRule {

    @OneToOne(mappedBy = "rule", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private RuleExecutionStats stats;

@Table(name = "dynamic_rules")
@Data
public class DynamicRule {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String productName;

    private String productId;

    private String productText;

    @Convert(converter = RuleJsonConverter.class)
    @Column(name = "rule_json", columnDefinition = "TEXT")
    private List<QueryDefinition> rule;

    @OneToOne(mappedBy = "rule", cascade = CascadeType.ALL, orphanRemoval = true)
    private RuleStatistic statistic;
    }
}