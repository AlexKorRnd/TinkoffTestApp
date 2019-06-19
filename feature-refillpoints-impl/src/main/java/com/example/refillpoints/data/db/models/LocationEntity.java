package com.example.refillpoints.data.db.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = LocationEntity.TABLE_NAME)
public class LocationEntity {

    public static final String TABLE_NAME = "location";
    public static final String ID_FIELD_NAME = TABLE_NAME + "_id";

    @DatabaseField(generatedId = true)
    long id;

    @DatabaseField
    double latitude;

    @DatabaseField
    double longitude;

    public LocationEntity() {
    }

    public LocationEntity(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
}
