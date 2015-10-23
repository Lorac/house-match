package ca.ulaval.glo4003.housematch.domain.property;

import ca.ulaval.glo4003.housematch.domain.CardinalDirection;

public class PropertyDetails {

    private PropertyStyle propertyStyle;
    private String priceDetails;
    private Integer numberOfBedrooms;
    private String bedroomDetails;
    private Integer numberOfBathrooms;
    private String bathroomDetails;
    private Integer totalNumberOfRooms;
    private Integer numberOfLevels;
    private Integer numberOfHalfbaths;
    private Integer livingSpaceAreaInSquareFeet;
    private Integer buildingDimensionsInSquareFeet;
    private Integer lotDimensionsInSquareFeet;
    private Integer yearOfConstruction;
    private Integer municipalAssessment;
    private Integer floorNumber;
    private Integer numberOfExteriorParkingSpaces;
    private Integer numberOfInteriorParkingSpaces;
    private PropertyOwnershipType ownershipType;
    private CardinalDirection backyardDirection;

    public PropertyStyle getPropertyStyle() {
        return propertyStyle;
    }

    public void setPropertyStyle(PropertyStyle propertyStyle) {
        this.propertyStyle = propertyStyle;
    }

    public String getPriceDetails() {
        return priceDetails;
    }

    public void setPriceDetails(String priceDetail) {
        this.priceDetails = priceDetail;
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

    public Integer getNumberOfExteriorParkingSpaces() {
        return numberOfExteriorParkingSpaces;
    }

    public void setNumberOfExteriorParkingSpaces(Integer numberOfExteriorParking) {
        this.numberOfExteriorParkingSpaces = numberOfExteriorParking;
    }

    public Integer getNumberOfInteriorParkingSpaces() {
        return numberOfInteriorParkingSpaces;
    }

    public void setNumberOfInteriorParkingSpaces(Integer numberOfInteriorParking) {
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
