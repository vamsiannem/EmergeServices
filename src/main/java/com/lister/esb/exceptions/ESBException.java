package com.lister.esb.exceptions;

import com.lister.esb.model.DataFormat;

public class ESBException extends Exception {

    private Throwable exceptionMessage;
    private DataFormat dataFormat;
    private String inputData;
    private String businessException;

    public ESBException(String inputData, DataFormat dataFormat, Throwable exceptionMessage, String businessException) {
        this.exceptionMessage = exceptionMessage;
        this.inputData = inputData;
        this.dataFormat = dataFormat;
        this.businessException = businessException;
    }

    public Throwable getExceptionMessage()
    {
        return this.exceptionMessage;
    }

    public void setExceptionMessage(Throwable exceptionMessage)
    {
        this.exceptionMessage = exceptionMessage;
    }

    public DataFormat getDataFormat() {
        return dataFormat;
    }

    public void setDataFormat(DataFormat dataFormat) {
        this.dataFormat = dataFormat;
    }

    public String getInputData() {
        return inputData;
    }

    public void setInputData(String inputData) {
        this.inputData = inputData;
    }

    public String getBusinessException() {
        return businessException;
    }

    public void setBusinessException(String businessException) {
        this.businessException = businessException;
    }
}
