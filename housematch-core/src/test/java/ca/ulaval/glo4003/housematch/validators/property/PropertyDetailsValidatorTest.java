package ca.ulaval.glo4003.housematch.validators.property;

import ca.ulaval.glo4003.housematch.domain.property.PropertyDetails;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PropertyDetailsValidatorTest {

    private static final Integer SAMPLE_POSITIVE_NUMBER = 1;
    private static final Integer SAMPLE_NEGATIVE_NUMBER = -1;
    private static final Integer SAMPLE_YEAR = 1950;

    private PropertyDetails propertyDetailsMock;

    private PropertyDetailsValidator propertyDetailsValidator;

    @Before
    public void init() throws Exception {
        propertyDetailsMock = mock(PropertyDetails.class);
        propertyDetailsValidator = new PropertyDetailsValidator();
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
    public void propertyDetailsValidationUsingValidValuesPassesValidation() throws Exception {
        try {
            propertyDetailsValidator.validatePropertyDetails(propertyDetailsMock);
        } catch (Exception e) {
            fail();
        }
    }

    @Test(expected = PropertyDetailsValidationException.class)
    public void validationWithNegativeBuildingDimensionsThrowsPropertyDetailsValidationException() throws Exception {
        when(propertyDetailsMock.getBuildingDimensionsInSquareFeet()).thenReturn(SAMPLE_NEGATIVE_NUMBER);
        propertyDetailsValidator.validatePropertyDetails(propertyDetailsMock);
    }

    @Test(expected = PropertyDetailsValidationException.class)
    public void validationWithNegativeLivingSpaceAreaThrowsPropertyDetailsValidationException() throws Exception {
        when(propertyDetailsMock.getLivingSpaceAreaInSquareFeet()).thenReturn(SAMPLE_NEGATIVE_NUMBER);
        propertyDetailsValidator.validatePropertyDetails(propertyDetailsMock);
    }

    @Test(expected = PropertyDetailsValidationException.class)
    public void validationWithNegativeLotDimensionsThrowsPropertyDetailsValidationException() throws Exception {
        when(propertyDetailsMock.getLotDimensionsInSquareFeet()).thenReturn(SAMPLE_NEGATIVE_NUMBER);
        propertyDetailsValidator.validatePropertyDetails(propertyDetailsMock);
    }

    @Test(expected = PropertyDetailsValidationException.class)
    public void validationWithNegativeMunicipalAssessmentThrowsPropertyDetailsValidationException() throws Exception {
        when(propertyDetailsMock.getMunicipalAssessment()).thenReturn(SAMPLE_NEGATIVE_NUMBER);
        propertyDetailsValidator.validatePropertyDetails(propertyDetailsMock);
    }

    @Test(expected = PropertyDetailsValidationException.class)
    public void validationWithNegativeNumberOfBathroomsThrowsPropertyDetailsValidationException() throws Exception {
        when(propertyDetailsMock.getNumberOfBathrooms()).thenReturn(SAMPLE_NEGATIVE_NUMBER);
        propertyDetailsValidator.validatePropertyDetails(propertyDetailsMock);
    }

    @Test(expected = PropertyDetailsValidationException.class)
    public void validationWithNegativeNumberOfBedroomsThrowsPropertDetailsValidationException() throws Exception {
        when(propertyDetailsMock.getNumberOfBedrooms()).thenReturn(SAMPLE_NEGATIVE_NUMBER);
        propertyDetailsValidator.validatePropertyDetails(propertyDetailsMock);
    }

    @Test(expected = PropertyDetailsValidationException.class)
    public void validationWithNegativeNumberOfExteriorParkingSpacesThrowsPropertyDetailsValidationException()
            throws Exception {
        when(propertyDetailsMock.getNumberOfExteriorParkingSpaces()).thenReturn(SAMPLE_NEGATIVE_NUMBER);
        propertyDetailsValidator.validatePropertyDetails(propertyDetailsMock);
    }

    @Test(expected = PropertyDetailsValidationException.class)
    public void validationWithNegativeNumberOfInteriorParkingSpacesThrowsPropertyDetailsValidationException()
            throws Exception {
        when(propertyDetailsMock.getNumberOfInteriorParkingSpaces()).thenReturn(SAMPLE_NEGATIVE_NUMBER);
        propertyDetailsValidator.validatePropertyDetails(propertyDetailsMock);
    }

    @Test(expected = PropertyDetailsValidationException.class)
    public void validationWithNegativeNumberOfLevelsThrowsPropertyDetailsValidationException() throws Exception {
        when(propertyDetailsMock.getNumberOfLevels()).thenReturn(SAMPLE_NEGATIVE_NUMBER);
        propertyDetailsValidator.validatePropertyDetails(propertyDetailsMock);
    }

    @Test(expected = PropertyDetailsValidationException.class)
    public void validationWithNegativeTotalNumberOfRoomsThrowsPropertyDetailsValidationException() throws Exception {
        when(propertyDetailsMock.getTotalNumberOfRooms()).thenReturn(SAMPLE_NEGATIVE_NUMBER);
        propertyDetailsValidator.validatePropertyDetails(propertyDetailsMock);
    }

    @Test(expected = PropertyDetailsValidationException.class)
    public void validationWithNegativeYearOfConstructionThrowsPropertyDetailsValidationException() throws Exception {
        when(propertyDetailsMock.getYearOfConstruction()).thenReturn(SAMPLE_NEGATIVE_NUMBER);
        propertyDetailsValidator.validatePropertyDetails(propertyDetailsMock);
    }
}
