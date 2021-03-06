package com.serverless.model;

import com.amazonaws.services.dynamodbv2.datamodeling.*;

@DynamoDBTable(tableName="LambdaConnection")
public class LambdaConnection {
     
    private String connectionId;
    private String functionName;
    private String requestId;
    private String requestTime;

    @DynamoDBHashKey(attributeName="connection_id")
    @DynamoDBAutoGeneratedKey
    public String getConnectionId() {
        return connectionId;
    }
    public LambdaConnection setConnectionId(String connectionId) {
        this.connectionId = connectionId;
        return this;
    }
     
    @DynamoDBAttribute(attributeName="function_name")
    public String getFunctionName() {
        return functionName;
    }
    public LambdaConnection setFunctionName(String functionName) {
        this.functionName = functionName;
        return this;
    }
     
    @DynamoDBAttribute(attributeName="request_id")
    public String getRequestId() {
        return requestId;
    }
    public LambdaConnection setRequestId(String requestId) {
        this.requestId = requestId;
        return this;
    }
     
    @DynamoDBRangeKey(attributeName="request_time")
    public String getRequestTime() {
        return requestTime;
    }
    public LambdaConnection setRequestTime(String requestTime) {
        this.requestTime = requestTime;
        return this;
    }
}