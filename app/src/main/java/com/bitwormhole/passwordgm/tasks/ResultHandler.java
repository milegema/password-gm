package com.bitwormhole.passwordgm.tasks;

public interface ResultHandler<T> {

    Result<T> handle(Result<T> res) throws Exception;

}
