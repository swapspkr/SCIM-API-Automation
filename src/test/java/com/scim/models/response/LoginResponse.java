package com.scim.models.response;

public class LoginResponse {

	private String access_token;
	private String refresh_token;
	private String token_type;
	private int expires_in;
	private int session_expires_in;
	private String scope;

	
	public LoginResponse() {}

	public String getAccess_token() {
		return access_token;
	}

	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}

	public String getRefresh_token() {
		return refresh_token;
	}

	public void setRefresh_token(String refresh_token) {
		this.refresh_token = refresh_token;
	}

	public String getToken_type() {
		return token_type;
	}

	public void setToken_type(String token_type) {
		this.token_type = token_type;
	}

	public int getExpires_in() {
		return expires_in;
	}

	public void setExpires_in(int expires_in) {
		this.expires_in = expires_in;
	}

	public int getSession_expires_in() {
		return session_expires_in;
	}

	public void setSession_expires_in(int session_expires_in) {
		this.session_expires_in = session_expires_in;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public LoginResponse(String access_token, String refresh_token, String token_type, int expires_in,
			int session_expires_in, String scope) {
		super();
		this.access_token = access_token;
		this.refresh_token = refresh_token;
		this.token_type = token_type;
		this.expires_in = expires_in;
		this.session_expires_in = session_expires_in;
		this.scope = scope;
	}

	@Override
	public String toString() {
		return "LoginResponse [access_token=" + access_token + ", refresh_token=" + refresh_token + ", token_type="
				+ token_type + ", expires_in=" + expires_in + ", session_expires_in=" + session_expires_in + ", scope="
				+ scope + "]";
	}


}
