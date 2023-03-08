package com.example.myrest.sql.crud;

import com.example.myrest.sql.model.MyTable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class MySelect {

    private final JdbcTemplate jdbcTemplate;

    public List<Map<String, Object>> execute(MyTable myTable){
        String selectQuery = selectQuery(myTable);
        log.info("Select query: {}", selectQuery);
        return jdbcTemplate
                .queryForList(selectQuery)
                .stream()
                .parallel()
                .map(this::convertKeysToCamelCase)
                .collect(Collectors.toList());
    }

    private static String selectQuery(MyTable myTable) {
        return "SELECT * FROM " + myTable.getName();
    }

    public Map<String, Object> convertKeysToCamelCase(Map<String, Object> inputMap) {
        Map<String, Object> outputMap = new HashMap<>();
        for (Map.Entry<String, Object> entry : inputMap.entrySet()) {
            String newKey = snakeToCamel(entry.getKey());
            outputMap.put(newKey, entry.getValue());
        }
        return outputMap;
    }

    public static String snakeToCamel(String str) {
        StringBuilder sb = new StringBuilder();
        boolean capitalize = false;
        for (int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);
            if (ch == '_') {
                capitalize = true;
            } else {
                if (capitalize) {
                    sb.append(Character.toUpperCase(ch));
                    capitalize = false;
                } else {
                    sb.append(ch);
                }
            }
        }
        return sb.toString();
    }
}
