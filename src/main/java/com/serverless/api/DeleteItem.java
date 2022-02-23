package com.serverless.api;

import java.util.Collections;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.serverless.ApiGatewayResponse;
import com.serverless.dal.CatalogItem;
import com.serverless.util.CommonUtil;

public class DeleteItem implements RequestHandler<Map<String, Object>, ApiGatewayResponse> {

	private static final Logger LOG = LogManager.getLogger(DeleteItem.class);

	@Override
	public ApiGatewayResponse handleRequest(Map<String, Object> input, Context context) {

		AmazonDynamoDB client = CommonUtil.getClient();
		LOG.info("This is a log, client :{}", client);
		DynamoDB dynamoDB = new DynamoDB(client);
		LOG.info("dynamoDB {}", dynamoDB.getTable("ProductCatalog"));
		try {
			Map<String, String> pathParameters = (Map<String, String>) input.get("pathParameters");
			LOG.info("Params :{}", pathParameters);
			String productId = pathParameters.get("id");
			DynamoDBMapper mapper = new DynamoDBMapper(client);
			CatalogItem itemRetrieved = mapper.load(CatalogItem.class, Integer.valueOf(productId));
			LOG.info("productId :{} and item to delete: {}", productId, itemRetrieved);
			mapper.delete(itemRetrieved);
			// Retrieve the item.
			LOG.info("delete success");
//			CatalogItem checkItem = mapper.load(CatalogItem.class, Integer.valueOf(productId));
//			LOG.info("Item retrieve {}", checkItem.toString());
			return ApiGatewayResponse.builder().setStatusCode(200).setObjectBody("Delete success")
					.setHeaders(Collections.singletonMap("X-Powered-By", "AWS Lambda & serverless")).build();
		} catch (Exception e) {
			LOG.info("Error {}", e.getMessage());
			return ApiGatewayResponse.builder().setStatusCode(500).setObjectBody("test bugg")
					.setHeaders(Collections.singletonMap("X-Powered-By", "AWS Lambda & serverless")).build();
		}

	}

}
