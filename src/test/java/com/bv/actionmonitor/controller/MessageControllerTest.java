package com.bv.actionmonitor.controller;

import com.bv.actionmonitor.ServiceInfo;
import com.bv.actionmonitor.model.Message;
import com.bv.actionmonitor.model.MessageBuilder;
import com.bv.actionmonitor.repo.MessageRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import static org.mockito.Mockito.times;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class MessageControllerTest {

    final static String testMessage = "message";
    final static String userName = "user1";

    @InjectMocks
    MessageController msgController;

    @Mock
    MessageRepository repo;

    @Mock
    ServiceInfo serviceInfo;

    @Mock
    SimpMessageHeaderAccessor accessor;

    @Test
    public void testRegister(){
        Message m = new MessageBuilder(testMessage).setSender(userName).setType(Message.MessageType.JOIN).build();
        Message result = msgController.register(m, accessor);

        assertEquals(testMessage, result.getContent());
        assertEquals(Message.MessageType.JOIN, result.getType());

        Mockito.verify(serviceInfo, times(1)).addUser(userName);
    }

    @Test
    public void testSendMessage(){
        Message m = new MessageBuilder(testMessage).setSender(userName).setType(Message.MessageType.REGULAR).build();
        Message result = msgController.sendMessage(m);

        assertEquals(testMessage, result.getContent());
        assertEquals(Message.MessageType.REGULAR, result.getType());
    }
}
