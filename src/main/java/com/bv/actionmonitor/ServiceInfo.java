package com.bv.actionmonitor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class ServiceInfo {

    private final Set<String> userList = new HashSet<>();

    @Value("${app.version}")
    private String appVersion;

    public void addUser(String user){ userList.add(user); }

    public String getVersion(){
        return appVersion;
    }

    public Set<String> getUsers(){ return userList; }
}
