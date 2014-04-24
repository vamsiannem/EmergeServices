package com.lister.esb.exceptions;

public class JsonException extends RuntimeException {
    private String jsonExceptionMessage;

    public JsonException(String jsonExceptionMessage) {
        this.jsonExceptionMessage = jsonExceptionMessage;
    }

    public String getJsonExceptionMessage()
    {
        return this.jsonExceptionMessage;
    }

    public void setJsonExceptionMessage(String jsonExceptionMessage)
    {
        this.jsonExceptionMessage = jsonExceptionMessage;
    }
}
