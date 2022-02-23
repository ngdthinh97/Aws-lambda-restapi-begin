package com.serverless.api;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.serverless.ApiGatewayResponse;
import com.serverless.dal.CatalogItem;
import com.serverless.util.CommonUtil;

public class ListItems implements RequestHandler<Map<String, Object>, ApiGatewayResponse> {

	private static final Logger LOG = LogManager.getLogger(ListItems.class);

	public ApiGatewayResponse handleRequest(Map<String, Object> input, Context context) {

		AmazonDynamoDB client = CommonUtil.getClient();
		LOG.info("This is a log, client :{}", client);
		DynamoDB dynamoDB = new DynamoDB(client);
		LOG.info("dynamoDB {}", dynamoDB.getTable("ProductCatalog"));
		try {
			DynamoDBMapper mapper = new DynamoDBMapper(client);
			LOG.info("Get Mapper {}", mapper.toString());
			DynamoDBScanExpression scanExp = new DynamoDBScanExpression();
			List<CatalogItem> results = mapper.scan(CatalogItem.class, scanExp);
			LOG.info("Items counted {}", results.size());
			return ApiGatewayResponse.builder().setStatusCode(200).setObjectBody(results)
					.setHeaders(Collections.singletonMap("X-Powered-By", "AWS Lambda & serverless")).build();
		} catch (Exception e) {
			LOG.info("Error {}", e.getMessage());
			return ApiGatewayResponse.builder().setStatusCode(500).setObjectBody("test bugg")
					.setHeaders(Collections.singletonMap("X-Powered-By", "AWS Lambda & serverless")).build();
		}
	}

	public static void main(String[] args) {
		test();
	}

	public static void test() {

		AmazonDynamoDB client = CommonUtil.getClient();
		LOG.info("This is a log, client :{}", client);
		DynamoDB dynamoDB = new DynamoDB(client);
		LOG.info("dynamoDB {}", dynamoDB.getTable("ProductCatalog"));
		try {
			DynamoDBMapper mapper = new DynamoDBMapper(client);
			DynamoDBScanExpression scanExp = new DynamoDBScanExpression();
			List<CatalogItem> results = mapper.scan(CatalogItem.class, scanExp);
			LOG.info("Items counted {}", results.size());
			LOG.info("Get Mapper {}", mapper.toString());
			// Retrieve the item.
			CatalogItem itemRetrieved = mapper.load(CatalogItem.class, 601);
			LOG.info("Item retrieve {}", itemRetrieved.toString());
		} catch (Exception e) {
			LOG.info("Error {}", e.getMessage());
		}
	}
}
