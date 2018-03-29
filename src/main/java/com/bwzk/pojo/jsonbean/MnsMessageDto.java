package com.bwzk.pojo.jsonbean;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.UUID;

;

public class MnsMessageDto<T> {
    private String type;
    private String uuid;
    private T data;
    private Integer mnsMessageId;

    private String queueName;
    private boolean bigMessage;
    private boolean encrypted;

    public String getUuid() {
        return uuid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    public void setUuid(String uuid) {
        this.uuid = uuid;
    }


    public T getData() {
        return data;
    }


    public void setData(T data) {
        this.data = data;
    }


    public MnsMessageDto() {
        super();
        this.uuid = UUID.randomUUID().toString();
    }


    public Integer getMnsMessageId() {
        return mnsMessageId;
    }


    public void setMnsMessageId(Integer mnsMessageId) {
        this.mnsMessageId = mnsMessageId;
    }


    public String getQueueName() {
        return queueName;
    }


    public void setQueueName(String queueName) {
        this.queueName = queueName;
    }


    public boolean isEncrypted() {
        return encrypted;
    }


    public void setEncrypted(boolean encrypted) {
        this.encrypted = encrypted;
    }

    public boolean isBigMessage() {
        return bigMessage;
    }

    public void setBigMessage(boolean bigMessage) {
        this.bigMessage = bigMessage;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }


}
