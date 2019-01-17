package com.serverless.util;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBAsyncClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.DescribeTableResult;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.model.ResourceNotFoundException;

public class DynamoDbConnection {

    private AmazonDynamoDB amazonDynamoDB;
    private DynamoDBMapper dynamoDBMapper;

    private DynamoDbConnection() {
    }

    public static DynamoDbConnection createConnection(){
        try {
            DynamoDbConnection dynamoDbConnectionLocal = new DynamoDbConnection();
            dynamoDbConnectionLocal.amazonDynamoDB = AmazonDynamoDBAsyncClientBuilder.standard().build();
            dynamoDbConnectionLocal.dynamoDBMapper = new DynamoDBMapper(dynamoDbConnectionLocal.amazonDynamoDB);
            return dynamoDbConnectionLocal;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private boolean createTable(Class clazz){
        try {
            CreateTableRequest createTableRequest = dynamoDBMapper.generateCreateTableRequest(clazz);
            createTableRequest.setProvisionedThroughput(new ProvisionedThroughput(5L, 5L));
            amazonDynamoDB.createTable(createTableRequest);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean save(Object object){
        try {
            dynamoDBMapper.save(object);
        } catch (ResourceNotFoundException resourceNotFoundException){
            if (createTable(object.getClass())){
                printLogForDynamoDb("Successfully Added Table " + object.getClass().getName());
            }else {
                printLogForDynamoDb("Fail Added Table " + object.getClass().getName());
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }


    private boolean checkTableExist(String tableName, Class clazz){
        try{
            printLogForDynamoDb(clazz.getAnnotations().toString());
            printLogForDynamoDb(clazz.getAnnotation(DynamoDBTable.class).toString());
        }catch (Exception ex){
            ex.printStackTrace();
        }


        try {
            DescribeTableResult describeTableResult = amazonDynamoDB.describeTable(tableName);
            if (describeTableResult == null){
                return false;
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }


    public void printLogForDynamoDb(String message){
        System.out.println("DynamoDB Message " + message);
    }

}
