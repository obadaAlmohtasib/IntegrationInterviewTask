package com.digitinary.customers.dto.response;

import java.util.HashMap;

import com.digitinary.customers.exception.BusinessException;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ResponseDTO {
	
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty
	private BusinessException error;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty
	private HashMap<String,Object> body = new HashMap<>();

	private ResponseDTO (builder b) {
		this.error = b.error;
		this.body = b.body;
	}

	public static class builder {
		private BusinessException error;
		private HashMap<String,Object> body = new HashMap<>();

		public builder error(BusinessException error) {
			this.error = error;
			return this;
		}

	//	public builder body(HashMap<String,Object> o) {
	//		this.body=o;
	//		return this;
	//	}

		public builder addTobody(String key, Object value) {
			this.body.put(key, value);
			return this;
		}

		public ResponseDTO build() {
			return new ResponseDTO(this);
		}
	}

}