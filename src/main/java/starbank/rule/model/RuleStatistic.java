package starbank.rule.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "rule_statistics")
@Data
@NoArgsConstructor
public class RuleStatistic {

    @Id
    private Long ruleId;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "rule_id")
    private DynamicRule rule;

    private long count = 0;

    public RuleStatistic(DynamicRule rule) {
        this.rule = rule;
        this.ruleId = rule.getId();
    }
}