package ca.ulaval.glo4003.housematch.utils;

public class VersionedValue<T> {

    private Integer version = 0;
    private T value;

    public VersionedValue(final T initialValue) {
        value = initialValue;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value, Integer version) {
        this.value = value;
        this.version = version;
    }

    public Integer getVersion() {
        return version;
    }
}
