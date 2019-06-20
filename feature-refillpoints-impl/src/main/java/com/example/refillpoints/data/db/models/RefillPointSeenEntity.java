package com.example.refillpoints.data.db.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = RefillPointSeenEntity.TABLE_NAME)
public class RefillPointSeenEntity {

    public static final String TABLE_NAME = "refill_points_seen";
    public static final String ID_FIELD_NAME = TABLE_NAME + "_id";

    @DatabaseField(id = true)
    private String pointId;

    @DatabaseField
    private boolean isSeen;

    public RefillPointSeenEntity() {
    }

    public RefillPointSeenEntity(String pointId, boolean isSeen) {
        this.pointId = pointId;
        this.isSeen = isSeen;
    }

    public String getPointId() {
        return pointId;
    }

    public boolean isSeen() {
        return isSeen;
    }
}
