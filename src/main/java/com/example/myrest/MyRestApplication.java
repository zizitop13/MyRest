package com.example.myrest;

import com.example.myrest.rest.RouterFactory;
import com.example.myrest.sql.api.MySchema;
import com.example.myrest.sql.api.SQL;
import com.example.myrest.sql.mysql.MySQL;
import com.example.myrest.sql.mysql.MySQLSchemaScanner;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

import javax.sql.DataSource;

@Slf4j
@EnableWebMvc
@SpringBootApplication
@ConfigurationProperties(prefix = "myrest")
public class MyRestApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyRestApplication.class, args);
    }

    @Bean
    public MySQLSchemaScanner mySqlSchemaScanner(DataSource dataSource, DataSourceProperties dataSourceProperties) {
        return new MySQLSchemaScanner(dataSource, dataSourceProperties);
    }

    @Bean
    @SneakyThrows
    public MySchema schema(MySQLSchemaScanner scanner) {
        return scanner.getDatabaseSchema()
                .print();
    }

    @Bean
    public RouterFunction<ServerResponse> routerFunction(RouterFactory routerFactory, MySchema schema){
        return routerFactory.init(schema);
    }

    @Bean
    public SQL sql(){
        return new MySQL();
    }


}
