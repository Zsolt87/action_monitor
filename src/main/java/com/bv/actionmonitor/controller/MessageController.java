package com.bv.actionmonitor.controller;

import com.bv.actionmonitor.ServiceInfo;
import com.bv.actionmonitor.model.Message;
import com.bv.actionmonitor.repo.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Handling incoming new connection messages and regular messages
 */
@org.springframework.stereotype.Controller
public class MessageController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageController.class);

    private MessageRepository repo;
    private ServiceInfo serviceInfo;

    @Autowired
    public void setMessageRepository(MessageRepository repo){
        this.repo = repo;
    }

    @Autowired
    public void setServiceInfo(ServiceInfo serviceInfo){
        this.serviceInfo = serviceInfo;
    }

    /**
     * Handling register messages from the client
     * @param message sent by the user which is empty for this one
     * @param headerAccessor
     * @return the message from the user as a model object
     */
    @MessageMapping("/actionmonitor.register")
    @SendTo("/topic/public")
    public Message register(@Payload Message message, SimpMessageHeaderAccessor headerAccessor){

        if(message.getType().equals(Message.MessageType.JOIN)){
            serviceInfo.addUser(message.getSender());
        }

        LOGGER.info(message.getSender() + " has joined to the actionmonitor.");
        headerAccessor.getSessionAttributes().put("username", message.getSender());
        return message;
    }

    /**
     * Logging the incoming messages and storing them in the H2 database
     * @param message sent by the user
     * @return the message from the user as a model object
     */
    @MessageMapping("/actionmonitor.send")
    @SendTo("/topic/public")
    public Message sendMessage(@Payload Message message){

        LOGGER.info("Message: " + message.getContent() + " at " + new Date(System.currentTimeMillis()) + " from " + message.getSender());
        repo.save(message);
        return message;
    }
}
