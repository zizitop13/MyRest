package com.example.myrest.sql.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@Setter
@RequiredArgsConstructor
public class MyTable {

    private final String name;
    private List<MyColumn> columns;
    private List<MyConstraint> constraints;

    public String select() {
        return "SELECT * FROM " + this.getName();
    }

    public String insert(Map<String, String> insertMap) {
        return "INSERT INTO " + this.getName() + " " +
                normalize(insertMap.keySet()
                        .stream()
                        .map(this::camelToSnake)
                        .collect(Collectors.toList())
                ) +
                " VALUES " + normalize(insertMap.values());
    }


    public String update(Map<String, String> updateMap) {
        return "UPDATE " + this.getName() + " SET " +
                updateMap.keySet().stream()
                        .map(key -> key + "=" + updateMap.get(key))
                        .collect(Collectors.joining(", ", "", ""));
    }

    public String delete(Object id) {
        return "DELETE FROM " + this.getName() + " WHERE " + getPkColumn().getName()
                + " = " + (id instanceof String ? "\"" + id + "\"" : id);
    }

    private MyColumn getPkColumn() {
        return columns.get(0);
    }


    private String normalize(Collection<?> body) {
        return body.toString().replace("[", "(").replace("]", ")");
    }

    private String camelToSnake(String input) {
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
