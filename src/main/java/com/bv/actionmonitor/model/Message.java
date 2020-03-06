package com.bv.actionmonitor.model;

import javax.persistence.*;

@Entity
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String content;
    private String sender;

    @Transient
    private MessageType type;

    public enum MessageType{
        REGULAR, JOIN
    }

    public Message(){}

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSender() { return sender; }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }

    public long getId() { return id; }

    public void setId(long id) { this.id = id; }

}
