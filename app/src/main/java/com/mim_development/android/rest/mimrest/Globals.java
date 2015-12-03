package com.mim_development.android.rest.mimrest;

import java.util.UUID;

public class Globals {

    public static final boolean SERVICE_CONNECTION_IS_SECURE = false;
    public static final String SERVICE_CONNECTION_SERVER = "192.168.1.7:8080";
    public static final String SERVICE_CONNECTION_PATH = "RESTPoc/rest";

    public static final int DEFAULT_CONNECTION_TIMEOUT_MILLIS = 8000;

    public static final UUID EMPTY_UUID = new UUID((long)0, (long)0);

    public enum HttpVerbs{
        GET,
        POST,
        DELETE,
        PUT
    };
}
