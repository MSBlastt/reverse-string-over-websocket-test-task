package com.task.config;

/**
 * Application level constants
 *
 * Created by Mikhail Kholodkov
 *         on July 2, 2018
 */
public final class Constants {

    private Constants() {
    }

    public static final String APPLICATION_DESTINATION_PREFIX = "/app";
    public static final String APPLICATION_DESTINATION_TOPIC_PREFIX = "/topic";
    public static final String STOMP_OVER_WEBSOCKET_ENDPOINT = "/websocket";

    public static final String REST_ENDPOINT_MAPPING = "/string/reverse";
    public static final String REST_ENDPOINT_MESSAGE_DESTINATION_MAPPING = "/topic/strings";

}
