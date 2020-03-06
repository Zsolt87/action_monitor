package com.bv.actionmonitor.controller;

import com.bv.actionmonitor.ServiceInfo;
import com.bv.actionmonitor.model.Message;
import com.bv.actionmonitor.model.MessageBuilder;
import com.bv.actionmonitor.repo.MessageRepository;
import org.json.simple.JSONArray;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class ServiceInfoControllerTest {

    private static final String version = "0.0.1";

    @InjectMocks
    ServiceInfoController serviceInfoController;

    @Mock
    MessageRepository repo;

    @Mock
    ServiceInfo serviceInfo;

    @Test
    public void testVersion(){
        when(serviceInfo.getVersion()).thenReturn(version);

        ResponseEntity<Object> result = serviceInfoController.getVersion();

        assertEquals(HttpStatus.OK, result.getStatusCode());

        Map<String, String> expected = new HashMap<String ,String>();
        expected.put("version", version);

        assertEquals(expected, result.getBody());
    }

    @Test
    public void testEmptyUserSet(){
        when(serviceInfo.getUsers()).thenReturn(new HashSet<>());

        ResponseEntity<Object> result = serviceInfoController.getUserList();
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(1, ((Map)result.getBody()).size());
        assertEquals(0, ((JSONArray)((Map)result.getBody()).get("users")).size());
    }

    @Test
    public void testOneUser(){

        Set<String> users = new HashSet<>();
        users.add("test_user");
        when(serviceInfo.getUsers()).thenReturn(users);

        ResponseEntity<Object> result = serviceInfoController.getUserList();
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(1, ((Map)result.getBody()).size());
        assertEquals(1, ((JSONArray)((Map)result.getBody()).get("users")).size());
    }

    @Test
    public void testGetUpTime(){
        ResponseEntity<Object> result = serviceInfoController.getUptime(false);
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    public void testGetUpTimeFormatted(){
        ResponseEntity<Object> result = serviceInfoController.getUptime(true);
        assertEquals(HttpStatus.OK, result.getStatusCode());

        Pattern p = Pattern.compile("[0-9]+ seconds");
        Matcher m = p.matcher(((Map)result.getBody()).get("uptime").toString());

        assertTrue(m.find());
    }

    @Test
    public void testStoredMessages(){
        List<Message> messages = new ArrayList<Message>();

        messages.add(
                new MessageBuilder("test_content")
                        .setId(1)
                        .setSender("test_sender")
                        .setType(Message.MessageType.REGULAR)
                        .build());

        when(repo.findAll()).thenReturn(messages);

        ResponseEntity<Object> result = serviceInfoController.getStoredMessages();
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(1, ((Map)(result.getBody())).size());
    }
}
