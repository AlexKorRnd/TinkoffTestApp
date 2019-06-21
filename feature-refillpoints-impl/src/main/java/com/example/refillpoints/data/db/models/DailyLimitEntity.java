package com.example.refillpoints.data.db.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = DailyLimitEntity.TABLE_NAME)
public class DailyLimitEntity {

    public static final String TABLE_NAME = "daily_limit";
    public static final String ID_FIELD_NAME = TABLE_NAME + "_id";

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(foreign = true, foreignAutoRefresh = true, columnName = PartnerEntity.ID_FIELD_FULL_NAME)
    private PartnerEntity partner;

    @DatabaseField(foreign = true, columnName = CurrencyEntity.CODE_FIELD_NAME)
    private CurrencyEntity currency;

    @DatabaseField
    private int min;

    public DailyLimitEntity() {
    }

    public DailyLimitEntity(PartnerEntity partner, CurrencyEntity currency, int min) {
        this.currency = currency;
        this.partner = partner;
        this.min = min;
    }

    public CurrencyEntity getCurrency() {
        return currency;
    }

    public int getMin() {
        return min;
    }
}
