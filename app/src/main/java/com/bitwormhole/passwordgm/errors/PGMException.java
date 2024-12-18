package com.bitwormhole.passwordgm.errors;

public class PGMException extends RuntimeException {

    private final PGMErrorCode code;

    public PGMException(PGMErrorCode _code) {
        super();
        this.code = _code;
    }


    public PGMException(String msg) {
        super(msg);
        this.code = PGMErrorCode.Unknown;
    }


    public PGMException(PGMErrorCode _code, Throwable e) {
        super(e);
        this.code = _code;
    }

    public PGMException(PGMErrorCode _code, String msg) {
        super(msg);
        this.code = _code;
    }

    public PGMException(PGMErrorCode _code, String msg, Throwable e) {
        super(msg, e);
        this.code = _code;
    }

    public PGMErrorCode getErrorCode() {
        PGMErrorCode ec = this.code;
        if (ec == null) {
            ec = PGMErrorCode.Unknown;
        }
        return ec;
    }
}
