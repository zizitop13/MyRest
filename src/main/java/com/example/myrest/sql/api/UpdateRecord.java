package com.example.myrest.sql.api;

import lombok.RequiredArgsConstructor;

import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor(staticName = "update")
public class UpdateRecord {

    private final Map<String, Object> updateBody;

    public String setRow() {
        return updateBody.entrySet().stream()
                .map(entry -> entry.getKey() + "=" + entry.getValue())
                .collect(Collectors.joining(", ", "", ""));
    }
}
