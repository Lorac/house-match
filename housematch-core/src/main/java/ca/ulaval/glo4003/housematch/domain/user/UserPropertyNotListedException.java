package ca.ulaval.glo4003.housematch.domain.user;

public class UserPropertyNotListedException extends Exception{


    /**
     * 
     */
    private static final long serialVersionUID = 383420875035241232L;

    public UserPropertyNotListedException() {
        super();
    }

    public UserPropertyNotListedException(final String message) {
        super(message);
    }
    
}
