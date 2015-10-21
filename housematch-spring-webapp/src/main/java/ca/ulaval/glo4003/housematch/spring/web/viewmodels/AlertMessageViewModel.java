package ca.ulaval.glo4003.housematch.spring.web.viewmodels;

public class AlertMessageViewModel extends ViewModel {

    public static final String NAME = "alertMessage";

    private String message;
    private AlertMessageType messageType;

    public AlertMessageViewModel(final String message, final AlertMessageType messageType) {
        this.message = message;
        this.messageType = messageType;
    }

    @Override
    public String getName() {
        return NAME;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public AlertMessageType getMessageType() {
        return messageType;
    }

    public void setMessageType(AlertMessageType messageType) {
        this.messageType = messageType;
    }

}
