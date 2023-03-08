package com.example.myrest.rest;

import com.example.myrest.sql.crud.MyInsert;
import com.example.myrest.sql.crud.MySelect;
import com.example.myrest.sql.model.MySchema;
import com.example.myrest.sql.model.MyTable;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.function.HandlerFunction;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

import java.util.List;
import java.util.Map;

import static org.springframework.web.servlet.function.RouterFunctions.route;


@Component
@RequiredArgsConstructor
public class RouterFactory {
    private final MySelect mySelect;
    private final MyInsert myInsert;

    public RouterFunction<ServerResponse> init(MySchema schema) {
        var builder = route();
        schema.getTables()
                .forEach(table -> {
                            String context = getContext(table);
                            builder.GET(context, createGetHandler(table))
                                   .POST(context, createPostHandler(table));
                        }

                );
        return builder.build();
    }

    private HandlerFunction<ServerResponse> createPostHandler(MyTable table) {
        return request -> {
            Map<String, String> body = request.body(Map.class);
            int updated = myInsert.execute(table, body);
            return ServerResponse.ok().body("{\"updated\": \"" + updated + "\"}");
        };
    }

    private String getContext(MyTable table) {
        return "/" + table.getName();
    }

    private HandlerFunction<ServerResponse> createGetHandler(MyTable myTable) {
        return request -> {
            List<Map<String, Object>> result = mySelect.execute(myTable);
            return ServerResponse.ok().body(result);
        };
    }

}
