package com.cdgs.temple.util;

import java.util.List;

public class ResponseDto<T> {

	private String result;
	private String stringData;
	private List<T> data;
	private String errorMessage;
	private int code;

	public ResponseDto() {
	}

	public ResponseDto(String result, String stringData, List<T> data, String errorMessage, int code) {
		this.result = result;
		this.stringData = stringData;
		this.data = data;
		this.errorMessage = errorMessage;
		this.code = code;
	}

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

	public List<T> getData() {
		return data;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public void setData(List<T> data) {
		this.data = data;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getStringData() {
		return stringData;
	}

	public void setStringData(String stringData) {
		this.stringData = stringData;
	}

}
