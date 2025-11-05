package starbank.rule.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import starbank.rule.model.DynamicRule;
import starbank.rule.model.QueryDefinition;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RuleDto {
    private UUID id;
    @JsonProperty("product_name")
    private String productName;
    @JsonProperty("product_id")
    private String productId;
    @JsonProperty("product_text")
    private String productText;
    private List<QueryDefinition> rule;

    public static RuleDto fromEntity(DynamicRule entity) {
        return new RuleDto(
                entity.getId(),
                entity.getProductName(),
                entity.getProductId(),
                entity.getProductText(),
                entity.getRule()
        );
    }
}