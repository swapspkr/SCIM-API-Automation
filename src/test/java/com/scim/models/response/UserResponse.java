package com.scim.models.response;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserResponse {

	private List<String> schemas;
	private String userName;
	private boolean active;
	private String displayName;
	private List<Email> emails;
	private String id;
	private Meta meta;

	public UserResponse() {
	}

	// ================= Constructor =================
	public UserResponse(List<String> schemas, String userName, boolean active, String displayName, List<Email> emails,
			String id, Meta meta) {
		this.schemas = schemas;
		this.userName = userName;
		this.active = active;
		this.displayName = displayName;
		this.emails = emails;
		this.id = id;
		this.meta = meta;
	}

	// ================= Getters and Setters =================
	public List<String> getSchemas() {
		return schemas;
	}

	public void setSchemas(List<String> schemas) {
		this.schemas = schemas;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public List<Email> getEmails() {
		return emails;
	}

	public void setEmails(List<Email> emails) {
		this.emails = emails;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Meta getMeta() {
		return meta;
	}

	public void setMeta(Meta meta) {
		this.meta = meta;
	}

	@Override
	public String toString() {
		return "UserResponse [schemas=" + schemas + ", userName=" + userName + ", active=" + active + ", displayName="
				+ displayName + ", emails=" + emails + ", id=" + id + ", meta=" + meta + "]";
	}

	// ================= Nested Classes =================
	public static class Email {
		private String type;
		private boolean primary;
		private String value;

		public Email() {
		}

		public Email(String type, boolean primary, String value) {
			this.type = type;
			this.primary = primary;
			this.value = value;
		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

		public boolean isPrimary() {
			return primary;
		}

		public void setPrimary(boolean primary) {
			this.primary = primary;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}

		@Override
		public String toString() {
			return "Email [type=" + type + ", primary=" + primary + ", value=" + value + "]";
		}
	}

	public static class Meta {
		private String resourceType;
		private String location;
		private String version;

		public Meta() {
		}

		public Meta(String resourceType, String location, String version) {
			this.resourceType = resourceType;
			this.location = location;
			this.version = version;
		}

		public String getResourceType() {
			return resourceType;
		}

		public void setResourceType(String resourceType) {
			this.resourceType = resourceType;
		}

		public String getLocation() {
			return location;
		}

		public void setLocation(String location) {
			this.location = location;
		}

		public String getVersion() {
			return version;
		}

		public void setVersion(String version) {
			this.version = version;
		}

		@Override
		public String toString() {
			return "Meta [resourceType=" + resourceType + ", location=" + location + ", version=" + version + "]";
		}
	}

	// ================= Builder Class =================
	public static class Builder {
		private List<String> schemas = new ArrayList<>();
		private String userName;
		private boolean active = true;
		private String displayName;
		private List<Email> emails = new ArrayList<>();
		private String id;
		private Meta meta;

		public Builder schemas(List<String> schemas) {
			this.schemas.addAll(schemas);
			return this;
		}

		public Builder addSchema(String schema) {
			this.schemas.add(schema);
			return this;
		}

		public Builder userName(String userName) {
			this.userName = userName;
			return this;
		}

		public Builder active(boolean active) {
			this.active = active;
			return this;
		}

		public Builder displayName(String displayName) {
			this.displayName = displayName;
			return this;
		}

		public Builder emails(List<Email> emails) {
			this.emails.addAll(emails);
			return this;
		}

		public Builder addEmail(String type, boolean primary, String value) {
			this.emails.add(new Email(type, primary, value));
			return this;
		}

		public Builder id(String id) {
			this.id = id;
			return this;
		}

		public Builder meta(String resourceType, String location, String version) {
			this.meta = new Meta(resourceType, location, version);
			return this;
		}

		public UserResponse build() {
			return new UserResponse(schemas, userName, active, displayName, emails, id, meta);
		}
	}
}