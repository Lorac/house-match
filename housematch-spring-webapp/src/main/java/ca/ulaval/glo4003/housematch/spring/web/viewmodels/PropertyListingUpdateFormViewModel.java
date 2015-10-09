package ca.ulaval.glo4003.housematch.spring.web.viewmodels;

public class PropertyListingUpdateFormViewModel extends ViewModel {
    public static final String VIEWMODEL_NAME = "propertyListingUpdateForm";

    private String test;

    public String getViewModelName() {
        return VIEWMODEL_NAME;
    }

    public String getTest() {
        return test;
    }

    public void setTest(String x) {
        test = x;
    }
}
