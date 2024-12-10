package com.digitinary.customers.exception;

public class BusinessException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private String code;
	private ErrorSeverity severity;

	public BusinessException() {
		super();
	}

//	public BusinessException(String message) {
//		super(message);
//	}

	public BusinessException(String message, ErrorSeverity severity) {
		super(message);
		this.severity = severity;
	}
	
	public BusinessException(String message, String code, ErrorSeverity severity) {
		super(message);
		this.code = code;
		this.severity = severity;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public ErrorSeverity getSeverity() {
		return severity;
	}

	public void setSeverity(ErrorSeverity severity) {
		this.severity = severity;
	}

	public static enum ErrorSeverity {

		DEBUG("DEBUG"),
		INFO("INFO"),
		WARN("WARN"),
		ERROR("ERROR");

		private ErrorSeverity(String value) {
			this.value = value;
		}

		private String value;

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}

	}

}
