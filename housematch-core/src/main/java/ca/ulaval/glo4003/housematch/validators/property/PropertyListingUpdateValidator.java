package ca.ulaval.glo4003.housematch.validators.property;

import org.apache.commons.lang3.ObjectUtils;

import ca.ulaval.glo4003.housematch.domain.property.PropertyDetails;

public class PropertyListingUpdateValidator {

    private static final Integer MININMUM_CONSTRUCTION_YEAR = 1900;

    public void validatePropertyListingUpdate(PropertyDetails propertyDetails)
            throws PropertyListingUpdateValidationException {

        validateDimensionValues(propertyDetails);
        validateRoomLayoutValues(propertyDetails);
        validateParkingSpaceValues(propertyDetails);
        validateMiscellaneousPropertyValues(propertyDetails);
    }

    private void validateDimensionValues(PropertyDetails propertyDetails)
            throws PropertyListingUpdateValidationException {
        if (ObjectUtils.compare(propertyDetails.getBuildingDimensionsInSquareFeet(), 0, true) <= 0) {
            throw new PropertyListingUpdateValidationException("Building dimensions must be greater than 0.");
        } else if (ObjectUtils.compare(propertyDetails.getLivingSpaceAreaInSquareFeet(), 0, true) <= 0) {
            throw new PropertyListingUpdateValidationException("Living space area must be greater than  0.");
        } else if (ObjectUtils.compare(propertyDetails.getLotDimensionsInSquareFeet(), 0, true) <= 0) {
            throw new PropertyListingUpdateValidationException("Lot dimensions must be greater than 0.");
        }
    }

    private void validateRoomLayoutValues(PropertyDetails propertyDetails)
            throws PropertyListingUpdateValidationException {
        if (ObjectUtils.compare(propertyDetails.getNumberOfBathrooms(), 0, true) < 0) {
            throw new PropertyListingUpdateValidationException(
                    "Number of bathrooms must be greater than or equal to 0.");
        } else if (ObjectUtils.compare(propertyDetails.getNumberOfBedrooms(), 0, true) < 0) {
            throw new PropertyListingUpdateValidationException(
                    "Number of bedrooms must be greater than or equal to 0.");
        } else if (ObjectUtils.compare(propertyDetails.getTotalNumberOfRooms(), 0, true) <= 0) {
            throw new PropertyListingUpdateValidationException("Total number of rooms must be greater than 0.");
        }
    }

    private void validateParkingSpaceValues(PropertyDetails propertyDetails)
            throws PropertyListingUpdateValidationException {
        if (ObjectUtils.compare(propertyDetails.getNumberOfExteriorParkingSpaces(), 0, true) < 0) {
            throw new PropertyListingUpdateValidationException(
                    "Number of exterior parking spaces must be greater than or equal to 0.");
        } else if (ObjectUtils.compare(propertyDetails.getNumberOfInteriorParkingSpaces(), 0, true) < 0) {
            throw new PropertyListingUpdateValidationException(
                    "Number of interior parking spaces must be greater than or equal to 0.");
        }
    }

    private void validateMiscellaneousPropertyValues(PropertyDetails propertyDetails)
            throws PropertyListingUpdateValidationException {
        if (ObjectUtils.compare(propertyDetails.getMunicipalAssessment(), 0, true) <= 0) {
            throw new PropertyListingUpdateValidationException("Municipal assessment must be greater than 0.");
        } else if (ObjectUtils.compare(propertyDetails.getYearOfConstruction(), MININMUM_CONSTRUCTION_YEAR, true) < 0) {
            throw new PropertyListingUpdateValidationException(
                    String.format("Year of construction must be greater than year $s.", MININMUM_CONSTRUCTION_YEAR));
        } else if (ObjectUtils.compare(propertyDetails.getNumberOfLevels(), 0, true) <= 0) {
            throw new PropertyListingUpdateValidationException("Number of levels must be greater than 0.");
        }
    }
}
