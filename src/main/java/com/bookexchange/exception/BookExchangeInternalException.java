package com.bookexchange.exception;

/**
 * Created by sheke on 10/19/2015.
 */
public class BookExchangeInternalException extends Exception {
    public BookExchangeInternalException(String message) {
        super(message);
    }

    public BookExchangeInternalException(String message, Throwable cause) {
        super(message, cause);
    }
}
