package starbank.rule.converter;

import starbank.rule.model.QueryDefinition;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

@Converter
@RequiredArgsConstructor
public class RuleJsonConverter implements AttributeConverter<List<QueryDefinition>, String> {

    private static final Logger logger = LoggerFactory.getLogger(RuleJsonConverter.class);
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(List<QueryDefinition> attribute) {
        if (attribute == null) {
            return null;
        }
        try {
            return objectMapper.writeValueAsString(attribute);
        } catch (JsonProcessingException e) {
            logger.error("Error converting rule to JSON string", e);
            throw new IllegalArgumentException("Error converting rule to JSON string", e);
        }
    }

    @Override
    public List<QueryDefinition> convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.isEmpty()) {
            return null;
        }
        try {
            return objectMapper.readValue(dbData, new TypeReference<>() {});
        } catch (IOException e) {
            logger.error("Error converting JSON string to rule", e);
            throw new IllegalArgumentException("Error converting JSON string to rule", e);
        }
    }
}