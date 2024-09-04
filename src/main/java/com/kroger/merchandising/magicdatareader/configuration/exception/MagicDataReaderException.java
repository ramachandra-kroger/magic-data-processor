package com.kroger.merchandising.magicdatareader.configuration.exception;

public class MagicDataReaderException extends Exception{
    public MagicDataReaderException(String message) {
        super(message);
    }

    public MagicDataReaderException(Exception exception) {
        super(exception);
    }

    public MagicDataReaderException(String message, Exception exception) {
        super(message, exception);
    }
}


