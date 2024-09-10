package com.kroger.merchandising.magicdatareader.utils;

public class Constants {

    public static final String API_VERSION = "/v1";
    public static final String PRCDLV = "/prcdlv" + API_VERSION;
    public static final String STORE_PRICE_DELIVERY = "/storeprice";
    public static final String STORE_PRICE_ACE_IMMEDIATES = "/storeprice/ace-immediates";
    public static final String STORE_PRICE_FULL_LOAD = "/storeprice/full-load";
    public static final String DUMMY_STORE = "/dummystore";
    public static final String STORE_PRICE = "/storeprice/{division}/{dateTime}";
    public static final String EMAIL_TO = "riya.patel@kroger.com";
    public static final String EMAIL_FROM = "noreply@kroger.com";

    public static final String ZERO = "0";
    public static final String NULL = null;
    public static final String T = "T";
    public static final String L = "L";
    public static final String V = "V";
    public static final String B = "B";
    public static final String W = "W";
    public static final String C = "C";
    public static final String N = "N";
    public static final String Y = "Y";
    public static final String P = "P";
    public static final String EMPTY = " ";
    public static final String UNIT = "Unit";
    public static final String WEIGHT = "Weight";

    public static final String STATIC_QUERY = "SELECT 1";

    public static final String UPSERT_PRICE_INFO = "UPSERT_PRICE_INFO";
    public static final String UPSERT_ACE_PRICE_INFO = "UPSERT_ACE_PRICE_INFO";
    public static final String DELETE_PRICE_INFO = "DELETE_PRICE_INFO";
    public static final String EVENT_SOURCE = "POSH";

    public static final String STORE_PRICE_API = "STORE_PRICE";
    public static final String STORE_PRICE_ACE = "STORE_PRICE_ACE_IMMEDIATES";
    public static final String STORE_PRICE_LOAD = "STORE_PRICE_FULL_LOAD";

    public static final String LIST_SIZE = "listSize";
    public static final String RECORD_COUNTER = "recordCounter";
    public static final String TOTAL_UPDATED_ITEMS = "totalUpdatedItems";

    public static final String STORE_PRICE_DELETES = "STORE_PRICE_DELETES";
    public static final String BASE = "BASE";

    public static final String DELETE_EXECUTION = "deletePriceExecution";

    public static final String PRICE_UPDATE_EXECUTION = "UpdatePriceExecution";

    public static final String UPDATE_SUCCESS_EVENTS_EXECUTION = "UpdateSuccessEventsExecution";

    public static final String UPDATE_FAILED_EVENTS_EXECUTION = "UpdateFailedEventsExecution";

    public static final String RESPONSE_MESSAGE = "The request has been accepted for processing.";

    private Constants() {
        super();
    }

}
