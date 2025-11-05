package starbank.rule.model;

import java.util.List;

@Data
public class QueryDefinition {
    private String query;
    private List<String> arguments;
    private boolean negate;
}