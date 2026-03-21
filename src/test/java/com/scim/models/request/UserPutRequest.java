package com.scim.models.request;


import java.util.List;



public class UserPutRequest {

	private List<String> schemas;
	private String id;
	private String userName;
	private String password;
	private boolean active;
	private String displayName;
	private List<Email> emails;

	public UserPutRequest(List<String> schemas, String id, String userName, String password, boolean active,
			String displayName, List<Email> emails) {
		super();
		this.schemas = schemas;
		this.id = id;
		this.userName = userName;
		this.password = password;
		this.active = active;
		this.displayName = displayName;
		this.emails = emails;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
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
		return "UserPutRequest [userName=" + userName + ", displayName=" + displayName + "]";
	}

	// =================== Builder ===================
	public static class Builder {
		private List<String> schemas;
		private String userName;
		private String password;
		private String displayName;
		private List<Email> emails;
		private String id;
		private boolean active;

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

		public Builder emails(List<Email> list) {
			this.emails = list;
			return this;
		}

		public Builder id(String id) {
			this.id = id;
			return this;
		}

		public Builder active(boolean active) {
			this.active = active;
			return this;
		}

		public UserPutRequest build() {
			return new UserPutRequest(schemas, id, userName, password, active, displayName, emails);
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
