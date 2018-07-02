package com.task.api.rest;

import com.task.config.Constants;
import com.task.dao.ReverseStringDao;
import com.task.domain.entity.ReverseString;
import com.task.domain.request.ReverseStringRequest;
import com.task.domain.response.ReverseStringResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Message controller.
 *
 * Awaits incoming requests over HTTP on "Constants.REST_ENDPOINT_MAPPING".
 * Handles request by reversing incoming string and inserting result to in-memory database (H2), then
 * queries whole database and prepares result set as response.
 *
 * Broadcasts response to subscribers over STOMP, listening on "Constants.REST_ENDPOINT_MESSAGE_DESTINATION_MAPPING"
 *
 * Created by Mikhail Kholodkov
 *         on July 2, 2018
 */
@Controller
public class MessageController {

    // Entity DAO
    private final ReverseStringDao reverseStringDao;

    @Autowired
    public MessageController(ReverseStringDao reverseStringDao) {
        this.reverseStringDao = reverseStringDao;
    }

    @MessageMapping(Constants.REST_ENDPOINT_MAPPING)
    @SendTo(Constants.REST_ENDPOINT_MESSAGE_DESTINATION_MAPPING)
    public ReverseStringResponse reverseString(@Valid ReverseStringRequest message) {
        // Reverse incoming string
        String reverseString = new StringBuilder(message.getValue()).reverse().toString();

        // Converting to entity
        ReverseString entity = new ReverseString();
        entity.setValue(reverseString);

        // Save entity to DB
        reverseStringDao.save(entity);

        // Query all Strings from DB
        List<String> strings = reverseStringDao.findAll()
                .stream()
                .map(ReverseString::getValue)
                .collect(Collectors.toList());

        // Submitting response back via Message handler (@SendTo)
        return new ReverseStringResponse(reverseString, strings);
    }

}
