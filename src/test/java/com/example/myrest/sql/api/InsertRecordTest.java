package com.example.myrest.sql.api;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.*;

class InsertRecordTest {

    @Test
    void rowsOrder() {

        InsertRecord updateRecord = InsertRecord.insert(Map.of(
                "1", 1,
                "2", 2,
                "3", 3,
                "4", 4,
                "5", 5
        ));

        String columnsRow = updateRecord.columnsRow();

        String valueRow = updateRecord.valuesRow();

        assertThat(columnsRow).isEqualTo(valueRow);
    }

    @Test
    void camelToSnake() {
        InsertRecord updateRecord = InsertRecord.insert(Map.of(
                "camelCaseFirst", 1,
                "camelCaseSecond", 2
        ));

        String columnsRow = updateRecord.columnsRow();

        assertThat(columnsRow).contains("camel_case_first", "camel_case_second");
    }
}