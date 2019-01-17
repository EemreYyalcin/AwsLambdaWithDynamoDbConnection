package com.serverless.handler;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.serverless.message.Response;
import com.serverless.message.ApiGatewayResponse;
import com.serverless.model.Event;
import com.serverless.model.LambdaConnection;
import com.serverless.util.DynamoDbConnection;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Map;

public class MessageHandler implements RequestHandler<Map<String, Object>, ApiGatewayResponse> {

    private static LambdaLogger LOG = null;

    private static  DynamoDbConnection dynamoDbConnection;

    private Item getItemById(String id) {

        return null;
    }


    private void saveExampleEventToDynamoDb(Context context) {
        dynamoDbConnection.save(new Event().setRequestId(context.getAwsRequestId()).setRequestTime(LocalDate.now().toString()).setFunctionName(context.getFunctionName()));
        dynamoDbConnection.save(new LambdaConnection().setRequestId(context.getAwsRequestId()).setFunctionName(context.getFunctionName()).setRequestTime(LocalDate.now().toString()));
    }


    @Override
    public ApiGatewayResponse handleRequest(Map<String, Object> input, Context context) {
        LOG = context.getLogger();
        LOG.log("received: " + input);

        if (dynamoDbConnection == null){
            dynamoDbConnection = DynamoDbConnection.createConnection();
        }

//		input.put("Item: ", getItemById("1").asMap());
        saveExampleEventToDynamoDb(context);
        Response responseBody = new Response("Message Received successfully!", input);
        return ApiGatewayResponse.builder()
                .setStatusCode(200)
                .setObjectBody(input)
                .setHeaders(Collections.singletonMap("X-Powered-By", "Solid-ICT"))
                .build();
    }


}
