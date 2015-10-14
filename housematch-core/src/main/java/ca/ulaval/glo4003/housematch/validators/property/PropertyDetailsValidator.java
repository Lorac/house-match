package ca.ulaval.glo4003.housematch.validators.property;

import org.apache.commons.lang3.ObjectUtils;

import ca.ulaval.glo4003.housematch.domain.property.PropertyDetails;

public class PropertyDetailsValidator {

    private static final Integer MININMUM_CONSTRUCTION_YEAR = 1900;

    public void validatePropertyDetails(PropertyDetails propertyDetails)
            throws PropertyDetailsValidationException {

        validateDimensionValues(propertyDetails);
        validateRoomLayoutValues(propertyDetails);
        validateParkingSpaceValues(propertyDetails);
        validateMiscellaneousPropertyValues(propertyDetails);
    }

    private void validateDimensionValues(PropertyDetails propertyDetails)
            throws PropertyDetailsValidationException {
        if (ObjectUtils.compare(propertyDetails.getBuildingDimensionsInSquareFeet(), 0, true) <= 0) {
            throw new PropertyDetailsValidationException("Building dimensions must be greater than 0.");
        } else if (ObjectUtils.compare(propertyDetails.getLivingSpaceAreaInSquareFeet(), 0, true) <= 0) {
            throw new PropertyDetailsValidationException("Living space area must be greater than  0.");
        } else if (ObjectUtils.compare(propertyDetails.getLotDimensionsInSquareFeet(), 0, true) <= 0) {
            throw new PropertyDetailsValidationException("Lot dimensions must be greater than 0.");
        }
    }

    private void validateRoomLayoutValues(PropertyDetails propertyDetails)
            throws PropertyDetailsValidationException {
        if (ObjectUtils.compare(propertyDetails.getNumberOfBathrooms(), 0, true) < 0) {
            throw new PropertyDetailsValidationException(
                    "Number of bathrooms must be greater than or equal to 0.");
        } else if (ObjectUtils.compare(propertyDetails.getNumberOfBedrooms(), 0, true) < 0) {
            throw new PropertyDetailsValidationException(
                    "Number of bedrooms must be greater than or equal to 0.");
        } else if (ObjectUtils.compare(propertyDetails.getTotalNumberOfRooms(), 0, true) <= 0) {
            throw new PropertyDetailsValidationException("Total number of rooms must be greater than 0.");
        }
    }

    private void validateParkingSpaceValues(PropertyDetails propertyDetails)
            throws PropertyDetailsValidationException {
        if (ObjectUtils.compare(propertyDetails.getNumberOfExteriorParkingSpaces(), 0, true) < 0) {
            throw new PropertyDetailsValidationException(
                    "Number of exterior parking spaces must be greater than or equal to 0.");
        } else if (ObjectUtils.compare(propertyDetails.getNumberOfInteriorParkingSpaces(), 0, true) < 0) {
            throw new PropertyDetailsValidationException(
                    "Number of interior parking spaces must be greater than or equal to 0.");
        }
    }

    private void validateMiscellaneousPropertyValues(PropertyDetails propertyDetails)
            throws PropertyDetailsValidationException {
        if (ObjectUtils.compare(propertyDetails.getMunicipalAssessment(), 0, true) <= 0) {
            throw new PropertyDetailsValidationException("Municipal assessment must be greater than 0.");
        } else if (ObjectUtils.compare(propertyDetails.getYearOfConstruction(), MININMUM_CONSTRUCTION_YEAR, true) < 0) {
            throw new PropertyDetailsValidationException(
                    String.format("Year of construction must be greater than year $s.", MININMUM_CONSTRUCTION_YEAR));
        } else if (ObjectUtils.compare(propertyDetails.getNumberOfLevels(), 0, true) <= 0) {
            throw new PropertyDetailsValidationException("Number of levels must be greater than 0.");
        }
    }
}
