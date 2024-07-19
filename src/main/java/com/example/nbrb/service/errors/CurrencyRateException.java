package com.example.nbrb.service.errors;

/**
 * Custom exception for handling errors related to currency rate operations.
 */
public class CurrencyRateException extends RuntimeException {

    /**
     * Constructs a new CurrencyRateException with the specified detail message.
     *
     * @param message the detail message
     */
    public CurrencyRateException(String message) {
        super(message);
    }

    /**
     * Constructs a new CurrencyRateException with the specified detail message and cause.
     *
     * @param message the detail message
     * @param cause   the cause of the exception
     */
    public CurrencyRateException(String message, Throwable cause) {
        super(message, cause);
    }
}
