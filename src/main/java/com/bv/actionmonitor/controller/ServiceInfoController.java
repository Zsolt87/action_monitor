package com.bv.actionmonitor.controller;

import com.bv.actionmonitor.ServiceInfo;
import com.bv.actionmonitor.model.Message;
import com.bv.actionmonitor.repo.MessageRepository;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.util.List;

/**
 * Providing extra service information via REST endpoints
 */

@RestController
public class ServiceInfoController {

    private ServiceInfo serviceInfo;
    private MessageRepository repo;

    @Autowired
    public void setRepo(MessageRepository repo) {this.repo = repo; }

    @Autowired
    public void setServiceInfo(ServiceInfo serviceInfo){
        this.serviceInfo = serviceInfo;
    }

    /**
     * Provides the version number for the service
     * @return String representation of the version number
     */
    @GetMapping(path = "/version", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getVersion()
    {
        JSONObject entity = new JSONObject();
        entity.put("version", serviceInfo.getVersion() );

        return new ResponseEntity<Object>(entity, HttpStatus.OK);
    }


    /**
     * Gives back the number of active users who sent at least one message during the application lifecycle
     * @return JSON object with user names in that
     */
    @GetMapping(path = "/users", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getUserList()
    {
        JSONObject entity = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        serviceInfo.getUsers().stream().forEach(e -> jsonArray.add(e));
        entity.put("users", jsonArray);

        return new ResponseEntity<Object>(entity, HttpStatus.OK);
    }

    /**
     * Get all the stored messages from the database
     * @return all the rows from the database with meta data
     */
    @GetMapping(path = "/storedMessages", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getStoredMessages(){
        List<Message> messages = repo.findAll();

        JSONObject entity = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        messages.stream().forEach(jsonArray::add);
        entity.put("messages", jsonArray);

        return new ResponseEntity<Object>(entity, HttpStatus.OK);
    }

    /**
     * Get the JVM uptime (when the application was started) in milliseconds or in seconds
     * @param format defines if the output is formatted for human consumption
     * @return the ms or sec in JSON format
     */
    @GetMapping(path = "/uptime", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getUptime(@RequestParam boolean format){
        RuntimeMXBean rb = ManagementFactory.getRuntimeMXBean();
        long uptime = rb.getUptime();

        JSONObject entity = new JSONObject();
        if(format){
            entity.put("uptime", uptime/1000 + " seconds");
            return new ResponseEntity<Object>(entity, HttpStatus.OK);
        }else {
            entity.put("uptime", uptime);
            return new ResponseEntity<Object>(entity, HttpStatus.OK);
        }

    }
}