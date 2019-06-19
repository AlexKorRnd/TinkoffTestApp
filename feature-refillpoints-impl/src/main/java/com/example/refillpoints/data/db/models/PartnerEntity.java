package com.example.refillpoints.data.db.models;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = PartnerEntity.TABLE_NAME)
public class PartnerEntity {

    public static final String TABLE_NAME = "partner";
    public static final String ID_FIELD_NAME = TABLE_NAME + "_id";

    @DatabaseField(id = true)
    private String id;

    @DatabaseField
    private String name;

    @DatabaseField
    private String picture;

    @DatabaseField
    private String url;

    @DatabaseField
    private boolean hasLocations;

    @DatabaseField
    private boolean isMomentary;

    @DatabaseField
    private String depositionDuration;

    @DatabaseField
    private String limitations;

    @DatabaseField
    private String pointType;

    @DatabaseField
    private String description;

    @DatabaseField
    private int moneyMin;

    @DatabaseField
    private int moneyMax;

    @DatabaseField
    private boolean hasPreferentialDeposition;

    @ForeignCollectionField(columnName = RefillPointEntity.ID_FIELD_NAME)
    private ForeignCollection<RefillPointEntity> refillPointEntities;

    @ForeignCollectionField(columnName = LimitEntity.ID_FIELD_NAME)
    private ForeignCollection<LimitEntity> limits;

    @ForeignCollectionField(columnName = DailyLimitEntity.ID_FIELD_NAME)
    private ForeignCollection<DailyLimitEntity> dailyLimit;

    public PartnerEntity() {
    }

    public PartnerEntity(String id, String name, String picture, String url, boolean hasLocations, boolean isMomentary, String depositionDuration, String limitations, String pointType, String description, int moneyMin, int moneyMax, boolean hasPreferentialDeposition) {
        this.id = id;
        this.name = name;
        this.picture = picture;
        this.url = url;
        this.hasLocations = hasLocations;
        this.isMomentary = isMomentary;
        this.depositionDuration = depositionDuration;
        this.limitations = limitations;
        this.pointType = pointType;
        this.description = description;
        this.moneyMin = moneyMin;
        this.moneyMax = moneyMax;
        this.hasPreferentialDeposition = hasPreferentialDeposition;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPicture() {
        return picture;
    }

    public String getUrl() {
        return url;
    }

    public boolean isHasLocations() {
        return hasLocations;
    }

    public boolean isMomentary() {
        return isMomentary;
    }

    public String getDepositionDuration() {
        return depositionDuration;
    }

    public String getLimitations() {
        return limitations;
    }

    public String getPointType() {
        return pointType;
    }

    public String getDescription() {
        return description;
    }

    public int getMoneyMin() {
        return moneyMin;
    }

    public int getMoneyMax() {
        return moneyMax;
    }

    public boolean isHasPreferentialDeposition() {
        return hasPreferentialDeposition;
    }
}
