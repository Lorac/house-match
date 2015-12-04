package ca.ulaval.glo4003.housematch.domain.picture;

import org.apache.commons.lang3.builder.EqualsBuilder;

public class Picture {
    private String pathToPicture;
    private PictureStatus status;

    public Picture(final String pathToPicture, final PictureStatus status) {
        this.pathToPicture = pathToPicture;
        this.status = status;
    }

    public void changeStatus(PictureStatus status) {
        this.status = status;
    }

    public PictureStatus getStatus() {
        return this.status;
    }

    @Override
    public int hashCode() {
        return Math.abs(pathToPicture.hashCode());
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Picture)) {
            return false;
        }
        if (object == this) {
            return true;
        }

        Picture picture = (Picture) object;
        return new EqualsBuilder().append(pathToPicture, picture.pathToPicture).isEquals();
    }
}
