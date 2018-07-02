package com.task.test;

import com.task.config.Constants;
import com.task.domain.response.ReverseStringResponse;
import com.task.test.helper.TestSessionHandler;
import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

/**
 * Basic test case to ensure correctness of "test" -> "tset" request/response via WebSocket (STOMP).
 *
 * Created by Mikhail Kholodkov
 *         on July 2, 2018
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MessageHandlerTest {

    @LocalServerPort
    private int port;

    private SockJsClient sockJsClient;
    private WebSocketStompClient stompClient;
    private WebSocketHttpHeaders headers;

    private CountDownLatch latch;

    @Before
    public void setup() {
        List<Transport> transports = Collections.singletonList(new WebSocketTransport(new StandardWebSocketClient()));

        this.headers = new WebSocketHttpHeaders();
        this.sockJsClient = new SockJsClient(transports);
        this.stompClient = new WebSocketStompClient(sockJsClient);
        this.stompClient.setMessageConverter(new MappingJackson2MessageConverter());

        this.latch = new CountDownLatch(1);
    }

    @Test
    public void sendStringEchoesReversedStringToSubscribersTest() throws Exception {
        TestSessionHandler handler = new TestSessionHandler(latch, "Test String");

        stompClient.connect("ws://localhost:{port}" + Constants.STOMP_OVER_WEBSOCKET_ENDPOINT, headers, handler, port);

        if (latch.await(3, TimeUnit.SECONDS)) {
            if (handler.getFailure() != null) {
                throw new AssertionError("", handler.getFailure());
            }
        }
        else {
            fail("No connection");
        }

        ReverseStringResponse message = handler.getLastAcceptedMessage();
        assertThat(message.getLastString(), CoreMatchers.is(new StringBuilder("Test String").reverse().toString()));
    }


}
