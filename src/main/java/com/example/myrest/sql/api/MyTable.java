package com.example.myrest.sql.api;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

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

    public String insert(InsertRecord insertRecord) {
        return "INSERT INTO " + this.getName() + " " + insertRecord.columnsRow() +
                " VALUES " + insertRecord.valuesRow();
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

}
