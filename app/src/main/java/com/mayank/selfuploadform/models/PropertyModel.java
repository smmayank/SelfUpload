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
    private Integer localityId;
    private Integer buildingId;
    private Integer floorNumber;
    private Integer totalFloors;
    private Integer ageOfProperty;

    private String entranceFacing;
    private String ageOfPropertyUnit;
    private String description;
    private String priceUnit;
    private String builtUpAreaUnit;
    private String availableFrom;

    private Boolean reservedParking;
    private Boolean cupboards;
    private Boolean pipelineGas;
    private Boolean isPriceNegotiable;

    private Double price;
    private Double brokerage;
    private Double builtUpArea;
    private Double carpetArea;
    private Double societyCharges;


    private Double priceFactor;
    private Double builtUpAreaFactor;
    private Double carpetAreaFactor;
    private Double brokerageFactor;
    private Double societyChargesFactor;

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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
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

    public Boolean getIsPriceNegotiable() {
        return isPriceNegotiable;
    }

    public void setIsPriceNegotiable(Boolean priceNegotiable) {
        isPriceNegotiable = priceNegotiable;
    }

    public Double getCarpetArea() {
        return carpetArea;
    }

    public void setCarpetArea(Double carpetArea) {
        this.carpetArea = carpetArea;
    }

    public Double getSocietyCharges() {
        return societyCharges;
    }

    public void setSocietyCharges(Double societyCharges) {
        this.societyCharges = societyCharges;
    }

    public Double getPriceFactor() {
        return priceFactor;
    }

    public void setPriceFactor(Double priceFactor) {
        this.priceFactor = priceFactor;
    }

    public Double getBuiltUpAreaFactor() {
        return builtUpAreaFactor;
    }

    public void setBuiltUpAreaFactor(Double builtUpAreaFactor) {
        this.builtUpAreaFactor = builtUpAreaFactor;
    }

    public Double getCarpetAreaFactor() {
        return carpetAreaFactor;
    }

    public void setCarpetAreaFactor(Double carpetAreaFactor) {
        this.carpetAreaFactor = carpetAreaFactor;
    }

    public Double getBrokerageFactor() {
        return brokerageFactor;
    }

    public void setBrokerageFactor(Double brokerageFactor) {
        this.brokerageFactor = brokerageFactor;
    }

    public Double getSocietyChargesFactor() {
        return societyChargesFactor;
    }

    public void setSocietyChargesFactor(Double societyChargesFactor) {
        this.societyChargesFactor = societyChargesFactor;
    }
}