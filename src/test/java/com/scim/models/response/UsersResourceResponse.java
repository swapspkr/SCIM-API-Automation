package com.scim.models.response;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UsersResourceResponse {
	private List<String> schemas;
	@JsonProperty("Resources")
	private List<UserResponse> Resources;
	private int totalResults;
	private int itemsPerPage;
	private int startIndex;

	public UsersResourceResponse() {
	}

	// ================= Constructor =================
	public UsersResourceResponse(List<String> schemas, List<UserResponse> resources, int totalResults, int itemsPerPage,
			int startIndex) {
		this.schemas = schemas;
		this.Resources = resources;
		this.totalResults = totalResults;
		this.itemsPerPage = itemsPerPage;
		this.startIndex = startIndex;
	}

	// ================= Getters and Setters =================
	public List<String> getSchemas() {
		return schemas;
	}

	public void setSchemas(List<String> schemas) {
		this.schemas = schemas;
	}

	public List<UserResponse> getResources() {
		return Resources;
	}

	public void setResources(List<UserResponse> resources) {
		this.Resources = resources;
	}

	public int getTotalResults() {
		return totalResults;
	}

	public void setTotalResults(int totalResults) {
		this.totalResults = totalResults;
	}

	public int getItemsPerPage() {
		return itemsPerPage;
	}

	public void setItemsPerPage(int itemsPerPage) {
		this.itemsPerPage = itemsPerPage;
	}

	public int getStartIndex() {
		return startIndex;
	}

	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}

	@Override
	public String toString() {
		return "UsersResourceResponse [schemas=" + schemas + ", Resources=" + Resources + ", totalResults="
				+ totalResults + ", itemsPerPage=" + itemsPerPage + ", startIndex=" + startIndex + "]";
	}

	// ================= Builder Class =================

	// ================= Builder Class =================
	public static class Builder {
		private List<String> schemas = new ArrayList<>();
		private List<UserResponse> Resources = new ArrayList<>();
		private int totalResults;
		private int itemsPerPage;
		private int startIndex;

		public Builder schemas(List<String> schemas) {
			this.schemas.addAll(schemas);
			return this;
		}

		public Builder addSchema(String schema) {
			this.schemas.add(schema);
			return this;
		}

		public Builder resources(List<UserResponse> Resources) {
			this.Resources.addAll(Resources);
			return this;
		}

		public Builder addResource(UserResponse resource) {
			this.Resources.add(resource);
			return this;
		}

		public Builder totalResults(int totalResults) {
			this.totalResults = totalResults;
			return this;
		}

		public Builder itemsPerPage(int itemsPerPage) {
			this.itemsPerPage = itemsPerPage;
			return this;
		}

		public Builder startIndex(int startIndex) {
			this.startIndex = startIndex;
			return this;
		}

		public UsersResourceResponse build() {
			return new UsersResourceResponse(schemas, Resources, totalResults, itemsPerPage, startIndex);
		}
	}
}
