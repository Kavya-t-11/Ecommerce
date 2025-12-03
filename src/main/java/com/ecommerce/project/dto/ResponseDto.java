package com.ecommerce.project.dto;

import lombok.Data;

@Data
public class ResponseDto {

	private Integer statusCode;
	private String status;
	private String message;
	private Object data;
	private int totalPages;
	private long totalElements;
	
	 public Integer getStatusCode() {
	        return statusCode;
	    }

	    public void setStatusCode(Integer statusCode) {
	        this.statusCode = statusCode;
	    }

	    public String getStatus() {
	        return status;
	    }

	    public void setStatus(String status) {
	        this.status = status;
	    }

	    public String getMessage() {
	        return message;
	    }

	    public void setMessage(String message) {
	        this.message = message;
	    }

	    public Object getData() {
	        return data;
	    }

	    public void setData(Object data) {
	        this.data = data;
	    }

	    public int getTotalPages() {
	        return totalPages;
	    }

	    public void setTotalPages(int totalPages) {
	        this.totalPages = totalPages;
	    }

	    public long getTotalElements() {
	        return totalElements;
	    }

	    public void setTotalElements(long totalElements) {
	        this.totalElements = totalElements;
	    }
	
	
}
