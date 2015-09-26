package ca.ulaval.glo4003.housematch.email;

public class MailSendException extends RuntimeException {

    private static final long serialVersionUID = -520179227673703218L;

    public MailSendException(final String msg, final Exception e) {
        super(msg, e);
    }
}
