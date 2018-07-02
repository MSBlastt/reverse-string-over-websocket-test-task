package com.task.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * Configuration point for WebSocket Broker
 *
 * Created by Mikhail Kholodkov
 *         on July 2, 2018
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker(Constants.APPLICATION_DESTINATION_TOPIC_PREFIX);
        config.setApplicationDestinationPrefixes(Constants.APPLICATION_DESTINATION_PREFIX);
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint(Constants.STOMP_OVER_WEBSOCKET_ENDPOINT).withSockJS();
    }

}