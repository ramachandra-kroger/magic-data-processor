package com.kroger.merchandising.magicdatareader.service;



public interface FailedEventService {
    void handleBadRecord(String event);
}
