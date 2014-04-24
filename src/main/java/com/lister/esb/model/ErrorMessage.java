package com.lister.esb.model;

public class ErrorMessage implements Bean{

    public ErrorMessage(String inputData, Throwable exception) {
        this.inputData = inputData;
        this.exception = exception;
    }

    private String inputData;
    private Throwable exception;

    public String getInputData() {
        return inputData;
    }

    public void setInputData(String inputData) {
        this.inputData = inputData;
    }

    public Throwable getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }

    @Override
    public String toString() {
        StringBuilder stringBuffer = new StringBuilder(inputData.length() + exception.getMessage().length() + 30);
        stringBuffer.append("inputData: ").append(inputData).append(", exception: ").append(exception.getLocalizedMessage());
        return stringBuffer.toString();
    }
}
