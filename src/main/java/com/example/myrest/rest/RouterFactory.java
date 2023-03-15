package com.example.myrest.rest;

import com.example.myrest.sql.api.Dao;
import com.example.myrest.sql.api.Filter;
import com.example.myrest.sql.api.InsertRecord;
import com.example.myrest.sql.api.MySchema;
import com.example.myrest.sql.api.MyTable;
import com.example.myrest.sql.api.UpdateRecord;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.function.HandlerFunction;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import static org.springdoc.core.fn.builders.content.Builder.contentBuilder;
import static org.springdoc.core.fn.builders.requestbody.Builder.requestBodyBuilder;
import static org.springdoc.core.fn.builders.schema.Builder.schemaBuilder;
import static org.springdoc.webmvc.core.fn.SpringdocRouteBuilder.route;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON;


@Component
@RequiredArgsConstructor
public class RouterFactory {
    private final Dao dao;

    public RouterFunction<ServerResponse> init(MySchema schema) {
        var builder = route();
        schema.getTables()
                .forEach(table -> {
                            String context = getContext(table);
                            builder.GET(context, createGetHandler(table), ops -> ops.operationId("get_" + table.getName())
                                            .tag(table.getName()))
                                    .POST(context, createPostHandler(table), getBuilderConsumer(table))
                                    .POST(context + "/update", createUpdateHandler(table), ops -> ops.operationId("update_" + table.getName())
                                            .tag(table.getName()))
                                    .DELETE(context, createDeleteHandler(table), ops -> ops.operationId("delete_" + table.getName())
                                            .tag(table.getName()));
                        }

                );
        return builder.build();
    }

    private Consumer<org.springdoc.core.fn.builders.operation.Builder> getBuilderConsumer(MyTable table) {
        return ops -> ops
                .tag(table.getName())
                .operationId("post_" + table.getName())
                .description("Insert into " + table.getName())
                .requestBody(requestBodyBuilder()
                        .required(true)
                        .content(contentBuilder()
                                .schema(schemaBuilder()
                                        .example(table.getColumns()
                                                .stream()
                                                .map(myColumn -> "\"" + myColumn.getName() + "\": " + "random")
                                                .collect(Collectors.joining(",", "{ ", " }"))))

                                .mediaType(APPLICATION_JSON.toString())
                        ))
                .build();
    }

    private HandlerFunction<ServerResponse> createDeleteHandler(MyTable table) {
        return request -> {
            String id = (String) request.attribute("id").orElseThrow();
            int updated = dao.delete(table, Filter.builder()
                    .pk(Map.of("id", id))
                    .build());
            return ServerResponse.ok().body("{\"delete\": \"" + updated + "\"}");
        };
    }

    private HandlerFunction<ServerResponse> createUpdateHandler(MyTable table) {
        return request -> {
            UpdateRecord updateRecord = UpdateRecord.update(request.body(Map.class));
            int updated = dao.update(table, updateRecord);
            return ServerResponse.ok().body("{\"updated\": \"" + updated + "\"}");
        };
    }

    private HandlerFunction<ServerResponse> createPostHandler(MyTable table) {
        return request -> {
            InsertRecord body = InsertRecord.insert(request.body(Map.class));
            int updated = dao.insert(table, body);
            return ServerResponse.ok().body("{\"inserted\": \"" + updated + "\"}");
        };
    }

    private String getContext(MyTable table) {
        return "/" + table.getName();
    }

    private HandlerFunction<ServerResponse> createGetHandler(MyTable myTable) {
        return request -> {
            List<Map<String, Object>> result = dao.select(myTable, new Filter(), PageRequest.ofSize(400));
            return ServerResponse.ok().body(result);
        };
    }

}
