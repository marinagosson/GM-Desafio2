package com.greenmile.desafio2;

import java.util.Collection;
import java.util.List;

public class ApiResponseArray<T> {
	
	private String status;
    private String message;
    private Collection<T> data;
    
    public ApiResponseArray(String status, String message, Collection<T> data) {
		super();
		this.status = status;
		this.message = message;
		this.data = data;
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
	public Collection<T> getData() {
		return data;
	}
	public void setData(Collection<T> data) {
		this.data = data;
	}
	
	@Override
	public String toString() {
		return super.toString();
	}

    
}
