package com.serverless.api;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.serverless.ApiGatewayResponse;
import com.serverless.dal.CatalogItem;
import com.serverless.util.CommonUtil;

public class CreateItem implements RequestHandler<Map<String, Object>, ApiGatewayResponse> {
	private static final Logger LOG = LogManager.getLogger(CreateItem.class);

	public ApiGatewayResponse handleRequest(Map<String, Object> input, Context context) {
		AmazonDynamoDB client = CommonUtil.getClient();
		LOG.info("This is a log, client :{}", client);
		DynamoDB dynamoDB = new DynamoDB(client);
		LOG.info("dynamoDB {}", dynamoDB.getTable("ProductCatalog"));
		try {
			JsonNode body = new ObjectMapper().readTree((String) input.get("body"));
			LOG.info("This is a log, client :{}", body);
			CatalogItem item = new CatalogItem();
			item.setId(body.get("id").asInt());
			item.setTitle(body.get("title").asText());
			item.setISBN(body.get("ISBN").asText());
			Set<String> books = new HashSet<String>();
			body.get("bookAuthors").forEach(s ->{
				books.add(s.asText());
			});
			item.setBookAuthors(books);
			DynamoDBMapper mapper = new DynamoDBMapper(client);
			LOG.info("Get Mapper {}", mapper.toString());
			mapper.save(item);
			// Retrieve the item.
			CatalogItem itemRetrieved = mapper.load(CatalogItem.class, item.getId());
			LOG.info("Item retrieve {}", itemRetrieved.toString());
			return ApiGatewayResponse.builder().setStatusCode(200).setObjectBody(itemRetrieved)
					.setHeaders(Collections.singletonMap("X-Powered-By", "AWS Lambda & serverless")).build();
		} catch (Exception e) {
			LOG.info("Error {}", e.getMessage());
			return ApiGatewayResponse.builder().setStatusCode(500).setObjectBody("test bugg")
					.setHeaders(Collections.singletonMap("X-Powered-By", "AWS Lambda & serverless")).build();
		}
	}
}
