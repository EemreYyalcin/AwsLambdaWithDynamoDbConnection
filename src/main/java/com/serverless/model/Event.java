package com.serverless.model;

import com.amazonaws.services.dynamodbv2.datamodeling.*;

@DynamoDBTable(tableName="Event")
public class Event {
     
    private String eventId;
    private String functionName;
    private String requestId;
    private String requestTime;

    @DynamoDBHashKey(attributeName="event_id")
    @DynamoDBAutoGeneratedKey
    public String getEventId() {
        return eventId;
    }
    public Event setEventId(String eventId) {
        this.eventId = eventId;
        return this;
    }
     
    @DynamoDBAttribute(attributeName="function_name")
    public String getFunctionName() {
        return functionName;
    }
    public Event setFunctionName(String functionName) {
        this.functionName = functionName;
        return this;
    }
     
    @DynamoDBAttribute(attributeName="request_id")
    public String getRequestId() {
        return requestId;
    }
    public Event setRequestId(String requestId) {
        this.requestId = requestId;
        return this;
    }
     
    @DynamoDBRangeKey(attributeName="request_time")
    public String getRequestTime() {
        return requestTime;
    }
    public Event setRequestTime(String requestTime) {
        this.requestTime = requestTime;
        return this;
    }
}