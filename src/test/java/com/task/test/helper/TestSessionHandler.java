package com.task.test.helper;

import com.task.config.Constants;
import com.task.domain.request.ReverseStringRequest;
import com.task.domain.response.ReverseStringResponse;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;

import java.lang.reflect.Type;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Helper test class.
 *
 * Emulates StompSession handler. Writes result and/or error of subscription events.
 *
 * Created by Mikhail Kholodkov
 *         on July 2, 2018
 */
public class TestSessionHandler extends StompSessionHandlerAdapter {

    private final AtomicReference<Throwable> failure;
    private final AtomicReference<ReverseStringResponse> message;

    private final CountDownLatch latch;

    private final String testString;

    public TestSessionHandler(CountDownLatch latch, String testString) {
        this.latch = latch;
        this.testString = testString;
        this.failure = new AtomicReference<>();
        this.message = new AtomicReference<>();
    }

    public ReverseStringResponse getLastAcceptedMessage() {
        return message.get();
    }


    public Throwable getFailure() {
        return failure.get();
    }

    @Override
    public void afterConnected(final StompSession session, StompHeaders connectedHeaders) {
        session.subscribe(Constants.REST_ENDPOINT_MESSAGE_DESTINATION_MAPPING, new TestStompFrameHandler(session));

        try {
            ReverseStringRequest reverseStringRequest = new ReverseStringRequest(testString);

            session.send(Constants.APPLICATION_DESTINATION_PREFIX + Constants.REST_ENDPOINT_MAPPING, reverseStringRequest);
        } catch (Throwable t) {
            failure.set(t);
            latch.countDown();
        }
    }

    private class TestStompFrameHandler implements StompFrameHandler {

        private final StompSession session;

        TestStompFrameHandler(StompSession session) {
            this.session = session;
        }

        @Override
        public Type getPayloadType(StompHeaders headers) {
            return ReverseStringResponse.class;
        }

        @Override
        public void handleFrame(StompHeaders headers, Object payload) {
            ReverseStringResponse response = (ReverseStringResponse) payload;
            try {
                message.set(response);
            } catch (Throwable t) {
                failure.set(t);
            } finally {
                session.disconnect();
                latch.countDown();
            }
        }
    }
}
