package com.kroger.merchandising.magicdatareader.configuration.exception;

@FunctionalInterface
public interface ThrowableConsumer<T, E extends Exception> {
    void accept(T t) throws E;
}
