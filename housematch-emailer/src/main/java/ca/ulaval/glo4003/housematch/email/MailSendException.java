package ca.ulaval.glo4003.housematch.email;

public class MailSendException extends RuntimeException {

    private static final long serialVersionUID = -520179227673703218L;

    public MailSendException() {
        super();
    }

    public MailSendException(final String message, final Exception e) {
        super(message, e);
    }
}
