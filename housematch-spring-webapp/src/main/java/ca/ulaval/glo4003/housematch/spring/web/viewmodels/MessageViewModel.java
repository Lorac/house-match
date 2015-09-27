package ca.ulaval.glo4003.housematch.spring.web.viewmodels;

public class MessageViewModel {
    private String message;
    private MessageType messageType;

    public MessageViewModel(final String message, final MessageType messageType) {
        this.message = message;
        this.messageType = messageType;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }

}
