package com.br.backend.exception;

public class Message {

	private String developerMessage;
	private String userMessage;

	public Message(String developerMessage, String userMessage) {
		this.developerMessage = developerMessage;
		this.userMessage = userMessage;
	}

	public String getDeveloperMessage() {
		return developerMessage;
	}

	public void setDeveloperMessage(String developerMessage) {
		this.developerMessage = developerMessage;
	}

	public String getUserMessage() {
		return userMessage;
	}

	public void setUserMessage(String userMessage) {
		this.userMessage = userMessage;
	}

}