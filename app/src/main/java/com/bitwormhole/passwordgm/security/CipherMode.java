package com.bitwormhole.passwordgm.security;

public enum CipherMode {

    NONE,
    ECB, CBC, CTR, CFB, OFB,
    ;

    public static boolean requireIV(CipherMode m) {
        if (m != null) {
            switch (m) {
                case CBC:
                case CTR:
                case CFB:
                case OFB:
                    return true;
            }
        }
        return false;
    }
}
