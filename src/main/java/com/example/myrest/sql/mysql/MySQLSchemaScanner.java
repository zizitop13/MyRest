package com.example.myrest.sql.mysql;

import com.example.myrest.sql.api.MyTable;
import com.example.myrest.sql.api.MyColumn;
import com.example.myrest.sql.api.MyConstraint;
import com.example.myrest.sql.api.MySchema;
import com.example.myrest.sql.api.SchemaScanner;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;

import javax.sql.DataSource;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
public class MySQLSchemaScanner implements SchemaScanner {

    private final DataSource dataSource;

    private final DataSourceProperties dataSourceProperties;

    @SneakyThrows
    public MySchema getDatabaseSchema() {
        MySchema schema = null;
        try (Connection conn = dataSource.getConnection();
            ResultSet rs = conn.getMetaData().getCatalogs()) {
            String schemaName = extractSchemaNameFromURL(dataSourceProperties.getUrl());
            while (rs.next()) {
                String dbName = rs.getString("TABLE_CAT");
                if (dbName.equals(schemaName)) {
                    schema = new MySchema(dbName);
                    schema.setTables(getTables(schema, conn));
                    break;
                }
            }
        }
        return schema;
    }

    @SneakyThrows
    private String extractSchemaNameFromURL(String jdbcUrl) {
        try {
            URI uri = new URI(jdbcUrl.substring(5));
            String path = uri.getPath();
            if (path.startsWith("/")) {
                path = path.substring(1);
            }
            String[] parts = path.split("/");
            return parts[0];
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException("Invalid JDBC URL: " + jdbcUrl);
        }
    }

    private List<MyTable> getTables(MySchema schema, Connection conn) throws SQLException {
        List<MyTable> tables = new ArrayList<>();
        try (ResultSet rs = conn.getMetaData().getTables(schema.getName(), null, null, null)) {
            while (rs.next()) {
                String tableName = rs.getString("TABLE_NAME");
                MyTable table = new MyTable(tableName);
                table.setColumns(getColumns(schema, table, conn));
                table.setConstraints(getConstraints(schema, table, conn));
                tables.add(table);
            }
        }
        return tables;
    }

    private List<MyColumn> getColumns(MySchema schema, MyTable table, Connection conn) throws SQLException {
        List<MyColumn> columns = new ArrayList<>();
        Set<String> primaryKeys = getPrimaryKeys(schema, table, conn);
        try (ResultSet rs = conn.getMetaData().getColumns(schema.getName(), null, table.getName(), null)) {
            while (rs.next()) {
                String columnName = rs.getString("COLUMN_NAME");
                String columnType = rs.getString("TYPE_NAME");
                int columnSize = rs.getInt("COLUMN_SIZE");
                boolean isNullable = rs.getInt("NULLABLE") == DatabaseMetaData.columnNullable;
                boolean isPrimaryKey = primaryKeys.contains(columnName);
                MyColumn column = new MyColumn(columnName, columnType, columnSize, isNullable, isPrimaryKey);
                columns.add(column);
            }
        }
        return columns;
    }

    private Set<String> getPrimaryKeys(MySchema schema, MyTable table, Connection conn) throws SQLException {
        // Check if column is a primary key column
        Set<String> keys = new HashSet<>();
        ResultSet rsPrimaryKeys = conn.getMetaData().getPrimaryKeys(schema.getName(), null, table.getName());
        while (rsPrimaryKeys.next()) {
            String primaryKeyColumnName = rsPrimaryKeys.getString("COLUMN_NAME");
            keys.add(primaryKeyColumnName);
        }
        return keys;
    }

    private List<MyConstraint> getConstraints(MySchema schema, MyTable table, Connection conn) throws SQLException {
        List<MyConstraint> constraints = new ArrayList<>();
        try (ResultSet rs = conn.getMetaData().getExportedKeys(schema.getName(), null, table.getName())) {
            while (rs.next()) {
                String constraintName = rs.getString("FK_NAME");
                String referencedTableName = rs.getString("PKTABLE_NAME");
                String referencedColumnName = rs.getString("PKCOLUMN_NAME");
                String columnName = rs.getString("FKCOLUMN_NAME");
                MyConstraint constraint = new MyConstraint(constraintName, referencedTableName, referencedColumnName, columnName);
                constraints.add(constraint);
            }
        }
        return constraints;
    }
}