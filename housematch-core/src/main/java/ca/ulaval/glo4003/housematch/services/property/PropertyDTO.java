package ca.ulaval.glo4003.housematch.services.property;

public class PropertyDTO {
    
    private String info = "";
    
    public PropertyDTO(String info) {
        this.info = info;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

}
