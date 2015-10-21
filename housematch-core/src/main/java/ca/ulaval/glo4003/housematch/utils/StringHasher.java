package ca.ulaval.glo4003.housematch.utils;

import org.apache.commons.codec.digest.DigestUtils;

public class StringHasher {

    public String hash(String value) {
        return DigestUtils.sha256Hex(value);
    }

}
