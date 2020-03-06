package com.bv.actionmonitor.model;

public class MessageBuilder {

    private String content;
    private String sender;
    private long id;
    private Message.MessageType type;

    public MessageBuilder(String content){
        this.content = content;
    }

    public MessageBuilder setSender(String sender){
        this.sender = sender;
        return this;
    }

    public MessageBuilder setId(long id){
        this.id = id;
        return this;
    }

    public MessageBuilder setType(Message.MessageType type){
        this.type = type;
        return this;
    }

    public Message build(){
        Message m = new Message();
        m.setContent(this.content);
        m.setSender(this.sender);
        m.setType(this.type);
        m.setId(this.id);

        return  m;
    }
}
