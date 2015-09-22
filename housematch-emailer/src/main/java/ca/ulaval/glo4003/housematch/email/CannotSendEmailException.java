package ca.ulaval.glo4003.housematch.email;

import javax.mail.MessagingException;

public class CannotSendEmailException extends Exception {

    private static final long serialVersionUID = -5148984263391449317L;

    public CannotSendEmailException(final String string, final MessagingException e) {
        // TODO Auto-generated constructor stub
    }

}
