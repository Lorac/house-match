package ca.ulaval.glo4003.housematch.domain.property;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import ca.ulaval.glo4003.housematch.domain.CardinalDirection;

public class PropertyDetailsTest {

    private static final PropertyStyle SAMPLE_PROPERTY_STYLE = PropertyStyle.CHALET;
    private static final PropertyOwnershipType SAMPLE_OWNERSHIP_TYPE = PropertyOwnershipType.SOLE_OWNERSHIP;
    private static final CardinalDirection SAMPLE_CARDINAL_DIRECTION = CardinalDirection.SOUTH;
    private static final String SAMPLE_STRING = "abc";
    private static final Integer SAMPLE_INTEGER = 28;

    PropertyDetails propertyDetails;

    @Before
    public void init() {
        propertyDetails = new PropertyDetails();
    }

    @Test
    public void settingThePropertyStyleSetsThePropertyStyle() {
        propertyDetails.setPropertyStyle(SAMPLE_PROPERTY_STYLE);
        assertEquals(SAMPLE_PROPERTY_STYLE, propertyDetails.getPropertyStyle());
    }

    @Test
    public void settingThePriceDetailsSetsThePriceDetails() {
        propertyDetails.setPriceDetails(SAMPLE_STRING);
        assertEquals(SAMPLE_STRING, propertyDetails.getPriceDetails());
    }

    @Test
    public void settingTheNumberOfBedroomsSetsTheNumberOfBedrooms() {
        propertyDetails.setNumberOfBedrooms(SAMPLE_INTEGER);
        assertEquals(SAMPLE_INTEGER, propertyDetails.getNumberOfBedrooms());
    }

    @Test
    public void settingTheNumberOfBathroomsSetsTheNumberOfBathrooms() {
        propertyDetails.setNumberOfBathrooms(SAMPLE_INTEGER);
        assertEquals(SAMPLE_INTEGER, propertyDetails.getNumberOfBathrooms());
    }

    @Test
    public void settingTheTotalNumberOfRoomsSetsTheTotalNumberOfRooms() {
        propertyDetails.setTotalNumberOfRooms(SAMPLE_INTEGER);
        assertEquals(SAMPLE_INTEGER, propertyDetails.getTotalNumberOfRooms());
    }

    @Test
    public void settingTheNumberOfLevelsSetsTheNumberOfLevels() {
        propertyDetails.setNumberOfLevels(SAMPLE_INTEGER);
        assertEquals(SAMPLE_INTEGER, propertyDetails.getNumberOfLevels());
    }

    @Test
    public void settingTheNumberOfHalfbathsSetsTheNumberOfHalfbaths() {
        propertyDetails.setNumberOfHalfbaths(SAMPLE_INTEGER);
        assertEquals(SAMPLE_INTEGER, propertyDetails.getNumberOfHalfbaths());
    }

    @Test
    public void settingTheLivingSpaceAreaSetsTheLivingSpaceArea() {
        propertyDetails.setLivingSpaceAreaInSquareFeet(SAMPLE_INTEGER);
        assertEquals(SAMPLE_INTEGER, propertyDetails.getLivingSpaceAreaInSquareFeet());
    }

    @Test
    public void settingTheBuildingDimensionsSetsTheBuildingDimensions() {
        propertyDetails.setBuildingDimensionsInSquareFeet(SAMPLE_INTEGER);
        assertEquals(SAMPLE_INTEGER, propertyDetails.getBuildingDimensionsInSquareFeet());
    }

    @Test
    public void settingTheLotDimensionsSetsTheLotDimensions() {
        propertyDetails.setLotDimensionsInSquareFeet(SAMPLE_INTEGER);
        assertEquals(SAMPLE_INTEGER, propertyDetails.getLotDimensionsInSquareFeet());
    }

    @Test
    public void settingTheYearOfConstructionSetsTheYearOfConstruction() {
        propertyDetails.setYearOfConstruction(SAMPLE_INTEGER);
        assertEquals(SAMPLE_INTEGER, propertyDetails.getYearOfConstruction());
    }

    @Test
    public void settingTheMunicipalAssessmentSetsTheMunicipalAssessment() {
        propertyDetails.setMunicipalAssessment(SAMPLE_INTEGER);
        assertEquals(SAMPLE_INTEGER, propertyDetails.getMunicipalAssessment());
    }

    @Test
    public void settingTheFloorNumberSetsTheFloorNumber() {
        propertyDetails.setFloorNumber(SAMPLE_INTEGER);
        assertEquals(SAMPLE_INTEGER, propertyDetails.getFloorNumber());
    }

    @Test
    public void settingTheNumberOfExteriorParkingSpacesSetsTheNumberOfExteriorParkingSpaces() {
        propertyDetails.setNumberOfExteriorParkingSpaces(SAMPLE_INTEGER);
        assertEquals(SAMPLE_INTEGER, propertyDetails.getNumberOfExteriorParkingSpaces());
    }

    @Test
    public void settingTheNumberOfInteriorParkingSpacesSetsTheNumberOfInteriorParkingSpaces() {
        propertyDetails.setNumberOfInteriorParkingSpaces(SAMPLE_INTEGER);
        assertEquals(SAMPLE_INTEGER, propertyDetails.getNumberOfInteriorParkingSpaces());
    }

    @Test
    public void settingTheOwnershipTypeSetsTheOwnershipType() {
        propertyDetails.setOwnershipType(SAMPLE_OWNERSHIP_TYPE);
        assertEquals(SAMPLE_OWNERSHIP_TYPE, propertyDetails.getOwnershipType());
    }

    @Test
    public void settingTheBedroomDetailsSetsTheBedroomDetails() {
        propertyDetails.setBedroomDetails(SAMPLE_STRING);
        assertEquals(SAMPLE_STRING, propertyDetails.getBedroomDetails());
    }

    @Test
    public void settingTheBathroomDetailsStyleSetsTheBathroomDetails() {
        propertyDetails.setBathroomDetails(SAMPLE_STRING);
        assertEquals(SAMPLE_STRING, propertyDetails.getBathroomDetails());
    }

    @Test
    public void settingTheBackyardDirectionSetsTheBackyardDirection() {
        propertyDetails.setBackyardDirection(SAMPLE_CARDINAL_DIRECTION);
        assertEquals(SAMPLE_CARDINAL_DIRECTION, propertyDetails.getBackyardDirection());
    }
}
