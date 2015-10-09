package ca.ulaval.glo4003.housematch.domain.property;

public class PropertyListingDetails {

    // Listed here are the possible options for the extended details of a property
    static final Integer INVALID_NUMERAL = 0;
    static final String EMPTY_FIELD = "";
    String propertyStyle = EMPTY_FIELD;
    String priceDetail = EMPTY_FIELD;
    Integer numberOfBedrooms = INVALID_NUMERAL;
    String bedroomDetails = EMPTY_FIELD;
    Integer numberOfBathrooms = INVALID_NUMERAL;
    String bathroomDetails = EMPTY_FIELD;
    Integer totalNumberOfRooms = INVALID_NUMERAL;
    Integer numberOfLevels = INVALID_NUMERAL; // excludes basement
    Integer numberOfHalfbaths = INVALID_NUMERAL;
    String livingSpaceArea = EMPTY_FIELD;
    String buildingDimensions = EMPTY_FIELD;
    String lotDimensions = EMPTY_FIELD;
    Integer yearOfConstruction = INVALID_NUMERAL;
    Integer municipalAssessment = INVALID_NUMERAL;
    Integer locatedOnWhichFloor = INVALID_NUMERAL;
    Integer numberOfExteriorParking = INVALID_NUMERAL;
    Integer numberOfInteriorParking = INVALID_NUMERAL;
    String ownership = EMPTY_FIELD;
    String backyardFaces = EMPTY_FIELD;

    public PropertyListingDetails() {

    }

    public String getPropertyStyle() {
        return propertyStyle;
    }

    public void setPropertyStyle(String propertyStyle) {
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

    public String getLivingSpaceArea() {
        return livingSpaceArea;
    }

    public void setLivingSpaceArea(String livingSpaceArea) {
        this.livingSpaceArea = livingSpaceArea;
    }

    public String getBuildingDimensions() {
        return buildingDimensions;
    }

    public void setBuildingDimensions(String buildingDimensions) {
        this.buildingDimensions = buildingDimensions;
    }

    public String getLotDimensions() {
        return lotDimensions;
    }

    public void setLotDimensions(String lotDimensions) {
        this.lotDimensions = lotDimensions;
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

    public Integer getLocatedOnWhichFloor() {
        return locatedOnWhichFloor;
    }

    public void setLocatedOnWhichFloor(Integer locatedOnWichFloor) {
        this.locatedOnWhichFloor = locatedOnWichFloor;
    }

    public Integer getNumberOfExteriorParking() {
        return numberOfExteriorParking;
    }

    public void setNumberOfExteriorParking(Integer numberOfExteriorParking) {
        this.numberOfExteriorParking = numberOfExteriorParking;
    }

    public Integer getNumberOfInteriorParking() {
        return numberOfInteriorParking;
    }

    public void setNumberOfInteriorParking(Integer numberOfInteriorParking) {
        this.numberOfInteriorParking = numberOfInteriorParking;
    }

    public String getOwnership() {
        return ownership;
    }

    public void setOwnership(String ownership) {
        this.ownership = ownership;
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

    public String getBackyardFaces() {
        return backyardFaces;
    }

    public void setBackyardFaces(String backyardFaces) {
        this.backyardFaces = backyardFaces;
    }
}
