package ca.ulaval.glo4003.housematch.context;


public abstract class ContextBase {

    public void apply() throws Exception {
        applyFillers();
    }

    protected abstract void applyFillers() throws Exception;

}
