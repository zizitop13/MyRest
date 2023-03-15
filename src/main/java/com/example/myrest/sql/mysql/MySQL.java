package com.example.myrest.sql.mysql;

import com.example.myrest.sql.api.Filter;
import com.example.myrest.sql.api.InsertRecord;
import com.example.myrest.sql.api.MyTable;
import com.example.myrest.sql.api.SQL;
import com.example.myrest.sql.api.UpdateRecord;
import org.springframework.data.domain.Pageable;

public class MySQL implements SQL {


    @Override
    public String select(MyTable table, Filter filter, Pageable pageable) {
        return "SELECT * FROM " + table.getName() + limit(pageable);
    }

    @Override
    public String insert(MyTable table, InsertRecord insertRecord) {
        return "INSERT INTO " + table.getName() + " " +
                insertRecord.columnsRow() +
                " VALUES " + insertRecord.valuesRow();
    }

    @Override
    public String update(MyTable table, UpdateRecord updateRecord) {
        return "UPDATE " + table.getName() + " SET " +
                updateRecord.setRow();
    }

    @Override
    public String delete(MyTable table, Filter filter) {
        return "DELETE FROM " + table.getName() + " WHERE " + filter.eq(table.getPk());
    }

    private String limit(Pageable pageable) {
        return " LIMIT " + pageable.getOffset() + "," + pageable.getPageSize();
    }

}
