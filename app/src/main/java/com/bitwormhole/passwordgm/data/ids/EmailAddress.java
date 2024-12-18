package com.bitwormhole.passwordgm.data.ids;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class EmailAddress {

    private final String mAddress;

    private EmailAddress() {
        this.mAddress = "n@a";
    }

    public EmailAddress(String address) {
        this.mAddress = normalize(address);
    }

    private static String normalize(String address) {
        int i = address.indexOf('@');
        if (i < 1) {
            throw new NumberFormatException("bad email address: " + address);
        }
        return address.trim().toLowerCase();
    }

    @Override
    public boolean equals(@Nullable Object other) {
        if (other == null) {
            return false;
        }
        if (other == this) {
            return true;
        }
        if (other instanceof EmailAddress) {
            EmailAddress o2 = (EmailAddress) other;
            return o2.mAddress.equals(this.mAddress);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return this.toString().hashCode();
    }

    @NonNull
    @Override
    public String toString() {
        return this.mAddress;
    }
}
