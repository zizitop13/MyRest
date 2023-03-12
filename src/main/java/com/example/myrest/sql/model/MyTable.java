package com.example.myrest.sql.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class MyTable {

    private final String name;
    private List<MyColumn> columns;

    private List<MyConstraint> constraints;

    private List<MyColumn> primaryKey;

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

    public String delete(String pk) {
        return "DELETE FROM " + this.getName() + " WHERE " + getPk().get(0).getName()
                + " = " + pk;
    }

    private List<MyColumn> getPk() {
        return columns.stream()
                .filter(MyColumn::isPrimaryKey)
                .collect(Collectors.toList());
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
