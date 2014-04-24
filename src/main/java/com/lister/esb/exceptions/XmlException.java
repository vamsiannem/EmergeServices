package com.lister.esb.exceptions;

public class XmlException extends RuntimeException {

    private String xmlExceptionMessage;

    public XmlException(String xmlExceptionMessage)
    {
        this.xmlExceptionMessage = xmlExceptionMessage;
    }

    public String getXmlExceptionMessage()
    {
        return this.xmlExceptionMessage;
    }

    public void setXmlExceptionMessage(String xmlExceptionMessage)
    {
        this.xmlExceptionMessage = xmlExceptionMessage;
    }
}
