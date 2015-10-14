package ca.ulaval.glo4003.housematch.validators.property;

import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import ca.ulaval.glo4003.housematch.domain.property.PropertyDetails;

public class PropertyListingUpdateValidatorTest {

    private static final Integer SAMPLE_POSITIVE_NUMBER = 1;
    private static final Integer SAMPLE_NEGATIVE_NUMBER = -1;
    private static final Integer SAMPLE_YEAR = 1950;

    private PropertyDetails propertyDetailsMock;

    private PropertyListingUpdateValidator propertyListingUpdateValidator;

    @Before
    public void init() throws Exception {
        propertyDetailsMock = mock(PropertyDetails.class);
        propertyListingUpdateValidator = new PropertyListingUpdateValidator();
        stubMethods();
    }

    private void stubMethods() {
        when(propertyDetailsMock.getBuildingDimensionsInSquareFeet()).thenReturn(SAMPLE_POSITIVE_NUMBER);
        when(propertyDetailsMock.getLivingSpaceAreaInSquareFeet()).thenReturn(SAMPLE_POSITIVE_NUMBER);
        when(propertyDetailsMock.getLotDimensionsInSquareFeet()).thenReturn(SAMPLE_POSITIVE_NUMBER);
        when(propertyDetailsMock.getMunicipalAssessment()).thenReturn(SAMPLE_POSITIVE_NUMBER);
        when(propertyDetailsMock.getNumberOfLevels()).thenReturn(SAMPLE_POSITIVE_NUMBER);
        when(propertyDetailsMock.getTotalNumberOfRooms()).thenReturn(SAMPLE_POSITIVE_NUMBER);
        when(propertyDetailsMock.getYearOfConstruction()).thenReturn(SAMPLE_YEAR);
    }

    @Test
    public void propertyListingUpdateUsingValidValuesPassesValidation() throws Exception {
        try {
            propertyListingUpdateValidator.validatePropertyListingUpdate(propertyDetailsMock);
        } catch (Exception e) {
            fail();
        }
    }

    @Test(expected = PropertyListingUpdateValidationException.class)
    public void validationWithNegativeBuildingDimensionsThrowsPropertyListingUpdateValidationException()
            throws Exception {
        when(propertyDetailsMock.getBuildingDimensionsInSquareFeet()).thenReturn(SAMPLE_NEGATIVE_NUMBER);
        propertyListingUpdateValidator.validatePropertyListingUpdate(propertyDetailsMock);
    }

    @Test(expected = PropertyListingUpdateValidationException.class)
    public void validationWithNegativeLivingSpaceAreaThrowsPropertyListingUpdateValidationException() throws Exception {
        when(propertyDetailsMock.getLivingSpaceAreaInSquareFeet()).thenReturn(SAMPLE_NEGATIVE_NUMBER);
        propertyListingUpdateValidator.validatePropertyListingUpdate(propertyDetailsMock);
    }

    @Test(expected = PropertyListingUpdateValidationException.class)
    public void validationWithNegativeLotDimensionsThrowsPropertyListingUpdateValidationException() throws Exception {
        when(propertyDetailsMock.getLotDimensionsInSquareFeet()).thenReturn(SAMPLE_NEGATIVE_NUMBER);
        propertyListingUpdateValidator.validatePropertyListingUpdate(propertyDetailsMock);
    }

    @Test(expected = PropertyListingUpdateValidationException.class)
    public void validationWithNegativeMunicipalAssessmentThrowsPropertyListingUpdateValidationException()
            throws Exception {
        when(propertyDetailsMock.getMunicipalAssessment()).thenReturn(SAMPLE_NEGATIVE_NUMBER);
        propertyListingUpdateValidator.validatePropertyListingUpdate(propertyDetailsMock);
    }

    @Test(expected = PropertyListingUpdateValidationException.class)
    public void validationWithNegativeNumberOfBathroomsThrowsPropertyListingUpdateValidationException()
            throws Exception {
        when(propertyDetailsMock.getNumberOfBathrooms()).thenReturn(SAMPLE_NEGATIVE_NUMBER);
        propertyListingUpdateValidator.validatePropertyListingUpdate(propertyDetailsMock);
    }

    @Test(expected = PropertyListingUpdateValidationException.class)
    public void validationWithNegativeNumberOfBedroomsThrowsPropertyListingUpdateValidationException()
            throws Exception {
        when(propertyDetailsMock.getNumberOfBedrooms()).thenReturn(SAMPLE_NEGATIVE_NUMBER);
        propertyListingUpdateValidator.validatePropertyListingUpdate(propertyDetailsMock);
    }

    @Test(expected = PropertyListingUpdateValidationException.class)
    public void validationWithNegativeNumberOfExteriorParkingSpacesThrowsPropertyListingUpdateValidationException()
            throws Exception {
        when(propertyDetailsMock.getNumberOfExteriorParkingSpaces()).thenReturn(SAMPLE_NEGATIVE_NUMBER);
        propertyListingUpdateValidator.validatePropertyListingUpdate(propertyDetailsMock);
    }

    @Test(expected = PropertyListingUpdateValidationException.class)
    public void validationWithNegativeNumberOfInteriorParkingSpacesThrowsPropertyListingUpdateValidationException()
            throws Exception {
        when(propertyDetailsMock.getNumberOfInteriorParkingSpaces()).thenReturn(SAMPLE_NEGATIVE_NUMBER);
        propertyListingUpdateValidator.validatePropertyListingUpdate(propertyDetailsMock);
    }

    @Test(expected = PropertyListingUpdateValidationException.class)
    public void validationWithNegativeNumberOfLevelsThrowsPropertyListingUpdateValidationException() throws Exception {
        when(propertyDetailsMock.getNumberOfLevels()).thenReturn(SAMPLE_NEGATIVE_NUMBER);
        propertyListingUpdateValidator.validatePropertyListingUpdate(propertyDetailsMock);
    }

    @Test(expected = PropertyListingUpdateValidationException.class)
    public void validationWithNegativeTotalNumberOfRoomsThrowsPropertyListingUpdateValidationException()
            throws Exception {
        when(propertyDetailsMock.getTotalNumberOfRooms()).thenReturn(SAMPLE_NEGATIVE_NUMBER);
        propertyListingUpdateValidator.validatePropertyListingUpdate(propertyDetailsMock);
    }

    @Test(expected = PropertyListingUpdateValidationException.class)
    public void validationWithNegativeYearOfConstructionThrowsPropertyListingUpdateValidationException()
            throws Exception {
        when(propertyDetailsMock.getYearOfConstruction()).thenReturn(SAMPLE_NEGATIVE_NUMBER);
        propertyListingUpdateValidator.validatePropertyListingUpdate(propertyDetailsMock);
    }
}
