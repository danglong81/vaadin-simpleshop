package com.vaadin.incubator.simpleshop.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity
public class LogMsg extends AbstractPojo {

	public static enum LogLevel {
		DEBUG, WARNING, ERROR;
	}

	@Column(length = 3000)
	private String message;

	@Enumerated(EnumType.STRING)
	private LogLevel level;

	@Column(length = 1000)
	private String className;

	@Column(length = 500)
	private String methodName;

	private int lineNumber;

	public LogMsg() {

	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public LogLevel getLevel() {
		return level;
	}

	public void setLevel(LogLevel level) {
		this.level = level;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public int getLineNumber() {
		return lineNumber;
	}

	public void setLineNumber(int lineNumber) {
		this.lineNumber = lineNumber;
	}

}
