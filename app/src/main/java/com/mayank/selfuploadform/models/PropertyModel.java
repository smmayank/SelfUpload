package com.mayank.selfuploadform.models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by vikas-pc on 23/02/16
 */
public class PropertyModel extends RealmObject {

    private static final String TAG = "PropertyModel";
    @PrimaryKey
    private int id;
    private Integer propertyType;
    private Integer bhkType;
    private Integer bathroomCount;
    private Integer balconyCount;
    private String entranceFacing;
    private Integer localityId;
    private Integer buildingId;
    private Integer floorNumber;
    private Integer totalFloors;
    private Integer ageOfProperty;
    private String ageOfPropertyUnit;
    private Boolean reservedParking;
    private Boolean cupboards;
    private Boolean pipelineGas;
    private String description;
    private Long price;
    private String priceUnit;
    private Double brokerage;
    private Double builtUpArea;
    private String builtUpAreaUnit;
    private String availableFrom;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getPropertyType() {
        return propertyType;
    }

    public void setPropertyType(Integer propertyType) {
        this.propertyType = propertyType;
    }

    public Integer getBhkType() {
        return bhkType;
    }

    public void setBhkType(Integer bhkType) {
        this.bhkType = bhkType;
    }

    public Integer getBathroomCount() {
        return bathroomCount;
    }

    public void setBathroomCount(Integer bathroomCount) {
        this.bathroomCount = bathroomCount;
    }

    public Integer getBalconyCount() {
        return balconyCount;
    }

    public void setBalconyCount(Integer balconyCount) {
        this.balconyCount = balconyCount;
    }

    public String getEntranceFacing() {
        return entranceFacing;
    }

    public void setEntranceFacing(String entranceFacing) {
        this.entranceFacing = entranceFacing;
    }

    public Integer getLocalityId() {
        return localityId;
    }

    public void setLocalityId(Integer localityId) {
        this.localityId = localityId;
    }

    public Integer getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(Integer buildingId) {
        this.buildingId = buildingId;
    }

    public Integer getFloorNumber() {
        return floorNumber;
    }

    public void setFloorNumber(Integer floorNumber) {
        this.floorNumber = floorNumber;
    }

    public Integer getTotalFloors() {
        return totalFloors;
    }

    public void setTotalFloors(Integer totalFloors) {
        this.totalFloors = totalFloors;
    }

    public Integer getAgeOfProperty() {
        return ageOfProperty;
    }

    public void setAgeOfProperty(Integer ageOfProperty) {
        this.ageOfProperty = ageOfProperty;
    }

    public String getAgeOfPropertyUnit() {
        return ageOfPropertyUnit;
    }

    public void setAgeOfPropertyUnit(String ageOfPropertyUnit) {
        this.ageOfPropertyUnit = ageOfPropertyUnit;
    }

    public Boolean getReservedParking() {
        return reservedParking;
    }

    public void setReservedParking(Boolean reservedParking) {
        this.reservedParking = reservedParking;
    }

    public Boolean getPipelineGas() {
        return pipelineGas;
    }

    public void setPipelineGas(Boolean pipelineGas) {
        this.pipelineGas = pipelineGas;
    }

    public Boolean getCupboards() {
        return cupboards;
    }

    public void setCupboards(Boolean cupboards) {
        this.cupboards = cupboards;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public String getPriceUnit() {
        return priceUnit;
    }

    public void setPriceUnit(String priceUnit) {
        this.priceUnit = priceUnit;
    }

    public Double getBrokerage() {
        return brokerage;
    }

    public void setBrokerage(Double brokerage) {
        this.brokerage = brokerage;
    }

    public Double getBuiltUpArea() {
        return builtUpArea;
    }

    public void setBuiltUpArea(Double builtUpArea) {
        this.builtUpArea = builtUpArea;
    }

    public String getBuiltUpAreaUnit() {
        return builtUpAreaUnit;
    }

    public void setBuiltUpAreaUnit(String builtUpAreaUnit) {
        this.builtUpAreaUnit = builtUpAreaUnit;
    }

    public String getAvailableFrom() {
        return availableFrom;
    }

    public void setAvailableFrom(String availableFrom) {
        this.availableFrom = availableFrom;
    }
}
