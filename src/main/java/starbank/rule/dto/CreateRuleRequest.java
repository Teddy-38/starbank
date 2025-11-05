package starbank.rule.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import starbank.rule.model.QueryDefinition;

import java.util.List;

@Data
public class CreateRuleRequest {
    @JsonProperty("product_name")
    private String productName;
    @JsonProperty("product_id")
    private String productId;
    @JsonProperty("product_text")
    private String productText;
    private List<QueryDefinition> rule;
}