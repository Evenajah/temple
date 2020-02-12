package com.cdgs.temple.util;

public class ResponseTokenDto {

	private String result;
	private String token_type;
	private String access_token;
	private long account_id;
	private String username;
	private String roleName;

	public enum RESPONSE_RESULT {
		Success("Success"), Fail("Fail");
		private String res;

		private RESPONSE_RESULT(String res) {
			this.res = res;
		}

		public String getRes() {
			return this.res;
		}
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public ResponseTokenDto(String result, String token_type, String access_token, long account_id, String username,
			String roleName) {
		this.result = result;
		this.token_type = token_type;
		this.access_token = access_token;
		this.account_id = account_id;
		this.username = username;
		this.roleName = roleName;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getToken_type() {
		return token_type;
	}

	public void setToken_type(String token_type) {
		this.token_type = token_type;
	}

	public String getAccess_token() {
		return access_token;
	}

	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}

	public long getAccount_id() {
		return account_id;
	}

	public void setAccount_id(long account_id) {
		this.account_id = account_id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}
