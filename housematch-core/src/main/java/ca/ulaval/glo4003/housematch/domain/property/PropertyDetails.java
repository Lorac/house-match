package ca.ulaval.glo4003.housematch.domain.property;

import ca.ulaval.glo4003.housematch.domain.CardinalDirection;

public class PropertyDetails {

    PropertyStyle propertyStyle;
    String priceDetail;
    Integer numberOfBedrooms;
    String bedroomDetails;
    Integer numberOfBathrooms;
    String bathroomDetails;
    Integer totalNumberOfRooms;
    Integer numberOfLevels;
    Integer numberOfHalfbaths;
    Integer livingSpaceAreaInSquareFeet;
    Integer buildingDimensionsInSquareFeet;
    Integer lotDimensionsInSquareFeet;
    Integer yearOfConstruction;
    Integer municipalAssessment;
    Integer floorNumber;
    Integer numberOfExteriorParkingSpaces;
    Integer numberOfInteriorParkingSpaces;
    PropertyOwnershipType ownershipType;
    CardinalDirection backyardDirection;

    public PropertyStyle getPropertyStyle() {
        return propertyStyle;
    }

    public void setPropertyStyle(PropertyStyle propertyStyle) {
        this.propertyStyle = propertyStyle;
    }

    public String getPriceDetail() {
        return priceDetail;
    }

    public void setPriceDetail(String priceDetail) {
        this.priceDetail = priceDetail;
    }

    public Integer getNumberOfBedrooms() {
        return numberOfBedrooms;
    }

    public void setNumberOfBedrooms(Integer numberOfBedrooms) {
        this.numberOfBedrooms = numberOfBedrooms;
    }

    public Integer getNumberOfBathrooms() {
        return numberOfBathrooms;
    }

    public void setNumberOfBathrooms(Integer numberOfBathrooms) {
        this.numberOfBathrooms = numberOfBathrooms;
    }

    public Integer getTotalNumberOfRooms() {
        return totalNumberOfRooms;
    }

    public void setTotalNumberOfRooms(Integer totalNumberOfRooms) {
        this.totalNumberOfRooms = totalNumberOfRooms;
    }

    public Integer getNumberOfLevels() {
        return numberOfLevels;
    }

    public void setNumberOfLevels(Integer numberOfLevels) {
        this.numberOfLevels = numberOfLevels;
    }

    public Integer getNumberOfHalfbaths() {
        return numberOfHalfbaths;
    }

    public void setNumberOfHalfbaths(Integer numberOfHalfbaths) {
        this.numberOfHalfbaths = numberOfHalfbaths;
    }

    public Integer getLivingSpaceAreaInSquareFeet() {
        return livingSpaceAreaInSquareFeet;
    }

    public void setLivingSpaceAreaInSquareFeet(Integer livingSpaceAreaInSquareFeet) {
        this.livingSpaceAreaInSquareFeet = livingSpaceAreaInSquareFeet;
    }

    public Integer getBuildingDimensionsInSquareFeet() {
        return buildingDimensionsInSquareFeet;
    }

    public void setBuildingDimensionsInSquareFeet(Integer buildingDimensionsInSquareFeet) {
        this.buildingDimensionsInSquareFeet = buildingDimensionsInSquareFeet;
    }

    public Integer getLotDimensionsInSquareFeet() {
        return lotDimensionsInSquareFeet;
    }

    public void setLotDimensionsInSquareFeet(Integer lotDimensionsInSquareFeet) {
        this.lotDimensionsInSquareFeet = lotDimensionsInSquareFeet;
    }

    public Integer getYearOfConstruction() {
        return yearOfConstruction;
    }

    public void setYearOfConstruction(Integer yearOfConstruction) {
        this.yearOfConstruction = yearOfConstruction;
    }

    public Integer getMunicipalAssessment() {
        return municipalAssessment;
    }

    public void setMunicipalAssessment(Integer municipalAssessment) {
        this.municipalAssessment = municipalAssessment;
    }

    public Integer getFloorNumber() {
        return floorNumber;
    }

    public void setFloorNumber(Integer floorNumber) {
        this.floorNumber = floorNumber;
    }

    public Integer getNumberOfExteriorParking() {
        return numberOfExteriorParkingSpaces;
    }

    public void setNumberOfExteriorParking(Integer numberOfExteriorParking) {
        this.numberOfExteriorParkingSpaces = numberOfExteriorParking;
    }

    public Integer getNumberOfInteriorParking() {
        return numberOfInteriorParkingSpaces;
    }

    public void setNumberOfInteriorParking(Integer numberOfInteriorParking) {
        this.numberOfInteriorParkingSpaces = numberOfInteriorParking;
    }

    public PropertyOwnershipType getOwnershipType() {
        return ownershipType;
    }

    public void setOwnershipType(PropertyOwnershipType ownershipType) {
        this.ownershipType = ownershipType;
    }

    public String getBedroomDetails() {
        return bedroomDetails;
    }

    public void setBedroomDetails(String bedroomDetails) {
        this.bedroomDetails = bedroomDetails;
    }

    public String getBathroomDetails() {
        return bathroomDetails;
    }

    public void setBathroomDetails(String bathroomDetails) {
        this.bathroomDetails = bathroomDetails;
    }

    public CardinalDirection getBackyardDirection() {
        return backyardDirection;
    }

    public void setBackyardDirection(CardinalDirection backyardDirection) {
        this.backyardDirection = backyardDirection;
    }
}
