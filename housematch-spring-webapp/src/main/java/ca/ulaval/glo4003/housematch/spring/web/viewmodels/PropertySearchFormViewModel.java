package ca.ulaval.glo4003.housematch.spring.web.viewmodels;

import javax.validation.constraints.NotNull;

public class PropertySearchFormViewModel extends ViewModel {

    public static final String NAME = "propertySearchForm";

    @NotNull
    private String searchExpression;

    @Override
    public String getName() {
        return NAME;
    }

    public String getSearchExpression() {
        return searchExpression;
    }

    public void setSearchExpression(String username) {
        this.searchExpression = username;
    }

}
