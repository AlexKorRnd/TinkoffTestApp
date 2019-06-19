package com.example.refillpoints.data.db.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = CurrencyEntity.TABLE_NAME)
public class CurrencyEntity {

    public static final String TABLE_NAME = "currency";
    public static final String CODE_FIELD_NAME = TABLE_NAME + "_code";

    @DatabaseField(id = true)
    private int code;

    @DatabaseField
    private String name;

    public CurrencyEntity() {
    }

    public CurrencyEntity(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
