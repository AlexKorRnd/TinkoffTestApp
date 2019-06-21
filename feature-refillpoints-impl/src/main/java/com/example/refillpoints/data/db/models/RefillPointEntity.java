package com.example.refillpoints.data.db.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = RefillPointEntity.TABLE_NAME)
public class RefillPointEntity {

    public static final String TABLE_NAME = "refill_point";
    public static final String ID_FIELD_NAME = TABLE_NAME + "_id";

    @DatabaseField(generatedId = true)
    private long id;

    @DatabaseField
    private String externalId;

    @DatabaseField(foreign = true, foreignAutoRefresh = true, columnName = PartnerEntity.ID_FIELD_FULL_NAME)
    private PartnerEntity partner;

    @DatabaseField(foreign = true, foreignAutoRefresh = true, columnName = LocationEntity.ID_FIELD_NAME)
    private LocationEntity location;

    @DatabaseField(foreign = true, columnName = PointsInAreaEntities.ID_FIELD_NAME)
    private PointsInAreaEntities area;

    @DatabaseField
    private String workHours;

    @DatabaseField
    private String phones;

    @DatabaseField
    private String addresInfo;

    @DatabaseField
    private String fullAddress;

    public RefillPointEntity() {
    }

    public RefillPointEntity(String externalId, String workHours, String phones, String addresInfo, String fullAddress, PartnerEntity partner, LocationEntity location, PointsInAreaEntities area) {
        this.externalId = externalId;
        this.workHours = workHours;
        this.phones = phones;
        this.addresInfo = addresInfo;
        this.fullAddress = fullAddress;
        this.partner = partner;
        this.location = location;
        this.area = area;
    }

    public String getExternalId() {
        return externalId;
    }

    public String getWorkHours() {
        return workHours;
    }

    public String getPhones() {
        return phones;
    }

    public String getAddresInfo() {
        return addresInfo;
    }

    public String getFullAddress() {
        return fullAddress;
    }

    public PartnerEntity getPartner() {
        return partner;
    }

    public LocationEntity getLocation() {
        return location;
    }
}
