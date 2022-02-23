package com.serverless.util;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;

public class CommonUtil {

	public static AmazonDynamoDB getClient() {

		return AmazonDynamoDBClientBuilder.standard().withRegion("ap-southeast-1").build();

//		return AmazonDynamoDBClientBuilder.standard()
//				.withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(
//						"jeyvrqg3bl.execute-api.ap-southeast-1.amazonaws.com", "ap-southeast-1"))
//				.build();
	}
}