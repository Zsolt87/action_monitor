package com.bv.actionmonitor.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MessageBuilderTest {

    private final String content = "message";
    private final String sender = "sender";
    private final Message.MessageType type = Message.MessageType.REGULAR;
    private final long id = 1;

    @Test
    public void testMessageBuilder(){
        MessageBuilder b = new MessageBuilder(content).setSender(sender).setType(type);

        Message m = b.build();

        assertEquals( sender, m.getSender());
        assertEquals(content, m.getContent());
        assertEquals(type, m.getType());

    }
}