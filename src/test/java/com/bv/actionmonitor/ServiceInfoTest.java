package com.bv.actionmonitor;


import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ServiceInfoTest {

    ServiceInfo serviceInfo = new ServiceInfo();

    @Test
    public void testUserList(){
        serviceInfo.addUser("test_user1");
        serviceInfo.addUser("test_user2");

        assertEquals(2, serviceInfo.getUsers().size());
    }
}