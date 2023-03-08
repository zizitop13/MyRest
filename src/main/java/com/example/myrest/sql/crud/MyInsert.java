package com.example.myrest.sql.crud;

import com.example.myrest.sql.model.MyTable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class MyInsert {

    private final JdbcTemplate jdbcTemplate;

    public int execute(MyTable myTable, Map<String, String> body) {
        String insert = getInsert(myTable, body);
        log.info("Insert query: {}", insert);
        return jdbcTemplate.update(insert);
    }

    private String getInsert(MyTable myTable, Map<String, String> body) {
        return "INSERT INTO " + myTable.getName() + " " +
                 normalize(body.keySet()
                         .stream()
                         .map(this::camelToSnake)
                         .collect(Collectors.toList())
                 ) +
                " VALUES " + normalize(body.values());
    }

    private static String normalize(Collection<?> body) {
        return body.toString().replace("[", "(").replace("]", ")");
    }

    public String camelToSnake(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }
        StringBuilder result = new StringBuilder();
        result.append(Character.toLowerCase(input.charAt(0)));
        for (int i = 1; i < input.length(); i++) {
            char c = input.charAt(i);
            if (Character.isUpperCase(c)) {
                result.append('_');
                result.append(Character.toLowerCase(c));
            } else {
                result.append(c);
            }
        }
        return result.toString();
    }
}
