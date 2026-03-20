package com.scim.models.request;

import java.util.List;

public class UserRequest {

	private List<String> schemas;
	private String userName;
	private String password;
	private String displayName;
	private List<Email> emails;

	public UserRequest(List<String> schemas, String userName, String password, String displayName, List<Email> emails) {
		super();
		this.schemas = schemas;
		this.userName = userName;
		this.password = password;
		this.displayName = displayName;
		this.emails = emails;
	}

	// Getters and Setters
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	@Override
	public String toString() {
		return "UserRequest [userName=" + userName + ", displayName=" + displayName + "]";
	}

	// =================== Builder ===================
	public static class Builder {
		private List<String> schemas;
		private String userName;
		private String password;
		private String displayName;
		private List<Email> emails;

		public Builder schemas(List<String> schemas) {
			this.schemas = schemas;
			return this;
		}

		public Builder userName(String userName) {
			this.userName = userName;
			return this;
		}

		public Builder password(String password) {
			this.password = password;
			return this;
		}

		public Builder displayName(String displayName) {
			this.displayName = displayName;
			return this;
		}

		public Builder emails(List<Email> emails) {
			this.emails = emails;
			return this;
		}

		public UserRequest build() {
			return new UserRequest(schemas, userName, password, displayName, emails);
		}
	}

	// =================== Nested Email class ===================
	public static class Email {
		private String type;
		private String value;

		public Email(String type, String value) {
			this.type = type;
			this.value = value;
		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}

		@Override
		public String toString() {
			return "Email [type=" + type + ", value=" + value + "]";
		}
	}
}
