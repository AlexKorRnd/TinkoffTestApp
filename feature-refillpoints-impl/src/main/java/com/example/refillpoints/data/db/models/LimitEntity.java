package com.example.refillpoints.data.db.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = LimitEntity.TABLE_NAME)
public class LimitEntity {

    public static final String TABLE_NAME = "limit";
    public static final String ID_FIELD_NAME = TABLE_NAME + "_id";

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(foreign = true, columnName = PartnerEntity.ID_FIELD_FULL_NAME)
    private PartnerEntity partner;

    @DatabaseField(foreign = true, columnName = CurrencyEntity.CODE_FIELD_NAME)
    private CurrencyEntity currency;

    @DatabaseField
    private int min;

    @DatabaseField
    private int max;

    public LimitEntity() {
    }

    public LimitEntity(PartnerEntity partner, CurrencyEntity currencyEntity, int min, int max) {
        this.currency = currencyEntity;
        this.partner = partner;
        this.min = min;
        this.max = max;
    }

    public CurrencyEntity getCurrencyEntity() {
        return currency;
    }

    public int getMin() {
        return min;
    }

    public int getMax() {
        return max;
    }
}
