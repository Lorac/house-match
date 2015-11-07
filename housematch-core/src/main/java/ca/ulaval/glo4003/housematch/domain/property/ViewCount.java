package ca.ulaval.glo4003.housematch.domain.property;

public final class ViewCount {

    private int count = 0;

    public void increase() {
        count++;
    }

    public int getCount() {
        return count;
    }

}
