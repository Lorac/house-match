package ca.ulaval.glo4003.housematch.spring.web.viewmodels;

public class MessageViewModel {
    private String message;

    public MessageViewModel(final String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
