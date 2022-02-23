package com.serverless.dal;

import java.util.Set;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName = "ProductCatalog")
public class CatalogItem {

	private Integer id;
	private String title;
	private String ISBN;
	private Set<String> bookAuthors;

	@DynamoDBHashKey(attributeName = "Id")
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@DynamoDBAttribute(attributeName = "Title")
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@DynamoDBAttribute(attributeName = "ISBN")
	public String getISBN() {
		return ISBN;
	}

	public void setISBN(String iSBN) {
		ISBN = iSBN;
	}

	@DynamoDBAttribute(attributeName = "Authors")
	public Set<String> getBookAuthors() {
		return bookAuthors;
	}

	public void setBookAuthors(Set<String> bookAuthors) {
		this.bookAuthors = bookAuthors;
	}

	@Override
	public String toString() {
		return "CatalogItem [id=" + id + ", title=" + title + ", ISBN=" + ISBN + ", bookAuthors=" + bookAuthors + "]";
	}

}
