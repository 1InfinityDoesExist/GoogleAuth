package com.example.googleAuth.model;

import org.springframework.stereotype.Component;

@Component
public class ApiResponse {

	private Integer httpStatus;
	private String message;
	private Object response;

	public ApiResponse() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ApiResponse(Integer i, String message, Object response) {
		super();
		this.httpStatus = i;
		this.message = message;
		this.response = response;
	}

	public Integer getHttpStatus() {
		return httpStatus;
	}

	public void setHttpStatus(Integer httpStatus) {
		this.httpStatus = httpStatus;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getResponse() {
		return response;
	}

	public void setResponse(Object response) {
		this.response = response;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((httpStatus == null) ? 0 : httpStatus.hashCode());
		result = prime * result + ((message == null) ? 0 : message.hashCode());
		result = prime * result + ((response == null) ? 0 : response.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ApiResponse other = (ApiResponse) obj;
		if (httpStatus == null) {
			if (other.httpStatus != null)
				return false;
		} else if (!httpStatus.equals(other.httpStatus))
			return false;
		if (message == null) {
			if (other.message != null)
				return false;
		} else if (!message.equals(other.message))
			return false;
		if (response == null) {
			if (other.response != null)
				return false;
		} else if (!response.equals(other.response))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ApiResponse [httpStatus=" + httpStatus + ", message=" + message + ", response=" + response + "]";
	}

}
