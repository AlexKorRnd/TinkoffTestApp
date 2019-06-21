package com.example.refillpoints.data.db.models;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.sql.Timestamp;

// по хорошему надо разнести на разные таблицы точки, но уже нет времени

@DatabaseTable(tableName = PointsInAreaEntities.TABLE_NAME)
public class PointsInAreaEntities {

    public static final String TABLE_NAME = "points_area_entities";
    public static final String ID_FIELD_NAME = TABLE_NAME + "_id";

    @DatabaseField(generatedId = true)
    private long id;

    @DatabaseField
    private double centerLat;

    @DatabaseField
    private double centerLng;

    @DatabaseField
    private double topLeftLat;

    @DatabaseField
    private double topLeftLng;

    @DatabaseField
    private double topRightLat;

    @DatabaseField
    private double topRightLng;

    @DatabaseField
    private double bottomRightLat;

    @DatabaseField
    private double bottomRightLng;

    @DatabaseField
    private double bottomLeftLat;

    @DatabaseField
    private double bottomLeftLng;

    @ForeignCollectionField(columnName = RefillPointEntity.ID_FIELD_NAME)
    private ForeignCollection<RefillPointEntity> pointEntities;

    @DatabaseField
    private Timestamp timestamp;

    public PointsInAreaEntities() {
    }

    public PointsInAreaEntities(double centerLat, double centerLng, double topLeftLat, double topLeftLng, double topRightLat, double topRightLng, double bottomRightLat, double bottomRightLng, double bottomLeftLat, double bottomLeftLng) {
        this.centerLat = centerLat;
        this.centerLng = centerLng;
        this.topLeftLat = topLeftLat;
        this.topLeftLng = topLeftLng;
        this.topRightLat = topRightLat;
        this.topRightLng = topRightLng;
        this.bottomRightLat = bottomRightLat;
        this.bottomRightLng = bottomRightLng;
        this.bottomLeftLat = bottomLeftLat;
        this.bottomLeftLng = bottomLeftLng;
        timestamp = new Timestamp(System.currentTimeMillis());
    }

    public double getCenterLat() {
        return centerLat;
    }

    public double getCenterLng() {
        return centerLng;
    }

    public double getTopLeftLat() {
        return topLeftLat;
    }

    public double getTopLeftLng() {
        return topLeftLng;
    }

    public double getTopRightLat() {
        return topRightLat;
    }

    public double getTopRightLng() {
        return topRightLng;
    }

    public double getBottomRightLat() {
        return bottomRightLat;
    }

    public double getBottomRightLng() {
        return bottomRightLng;
    }

    public double getBottomLeftLat() {
        return bottomLeftLat;
    }

    public double getBottomLeftLng() {
        return bottomLeftLng;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public ForeignCollection<RefillPointEntity> getPointEntities() {
        return pointEntities;
    }
}
