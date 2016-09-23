package com.wongs.views.AirconditionerView;

/**
 * Created by sky on 2015/10/6.
 */

public class LXRequestConstants
{
    public enum  JSON_NODE
    {

        CONNECTED,
         DISCONNECTED,
         SCHEDULES,
          ZONE,
    }


    public static final String COMMAND = "Command";
    public static final String CONNECTION_CHANGED = "PublisherConnectionChanged";
    public static final String DATA_REQUEST = "Data Request";
    public static final String FRAMEWORK = "Android";
    public static final String PROPERTY_CHANGE = "Property Change";
    public static final String PUBLISHER_PRESENCE = "PublisherPresence";
    public static final String SERVER = "SERVER";
    public static final String TIMESTAMP = "timestamp";
}