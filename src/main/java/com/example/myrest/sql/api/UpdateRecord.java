package com.example.myrest.sql.api;

import lombok.RequiredArgsConstructor;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor(staticName = "update")
public class UpdateRecord {

    private final Map<String, Object> updateBody;

    public String columnsRow(){
        return normalize(updateBody.keySet()
                .stream()
                .map(this::camelToSnake)
                .collect(Collectors.toList())
        );
    }

    public String valuesRow(){
        return normalize(updateBody.values());
    }

    private String normalize(Collection<?> body) {
        return body.toString().replace("[", "(").replace("]", ")");
    }

    private String camelToSnake(String input) {
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
