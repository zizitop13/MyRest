package com.example.myrest.sql.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Filter {
    private Map<String, Object> pk;

    public String eq(MyPrimaryKey myPrimaryKey){
        String idName = myPrimaryKey.getColumns().get(0).getName();
        return idName + "=" + pk.get(idName);
    }
}
