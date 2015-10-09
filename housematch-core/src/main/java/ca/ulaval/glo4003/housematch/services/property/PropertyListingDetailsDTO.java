package ca.ulaval.glo4003.housematch.services.property;

public class PropertyListingDetailsDTO {

    private String info = "";

    public PropertyListingDetailsDTO(final String info) {
        this.info = info;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

}
