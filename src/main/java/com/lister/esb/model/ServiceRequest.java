package com.lister.esb.model;


import com.lister.esb.dto.BaseDTO;
import com.lister.esb.enums.ActionType;
import com.lister.esb.enums.SourceSystem;

public class ServiceRequest implements Request {

    private String inputMessage;

    private ActionType actionType;

    private Boolean isResponseRequired;

    private DataFormat inputDataFormat;

    private Class<? extends BaseDTO> dtoClass;

    private SourceSystem sourceSystem;

    private String procedureName;

    public SourceSystem getSourceSystem() {
        return sourceSystem;
    }

    public void setSourceSystem(SourceSystem sourceSystem) {
        this.sourceSystem = sourceSystem;
    }

    public Class<? extends BaseDTO> getDtoClass() {
        return dtoClass;
    }

    public void setDtoClass(Class<? extends BaseDTO> dtoClass) {
        this.dtoClass = dtoClass;
    }

    public ServiceRequest() {

    }

    public ServiceRequest(String inputMessage, ActionType actionType, Boolean responseRequired, DataFormat inputDataFormat, Class<? extends BaseDTO> dtoClass, SourceSystem sourceSystem, String procedureName) {
        this.inputMessage = inputMessage;
        this.actionType = actionType;
        isResponseRequired = responseRequired;
        this.inputDataFormat = inputDataFormat;
        this.dtoClass = dtoClass;
        this.sourceSystem = sourceSystem;
        this.procedureName = procedureName;
    }

    public String getInputMessage() {
        return inputMessage;
    }

    public void setInputMessage(String inputMessage) {
        this.inputMessage = inputMessage;
    }

    public boolean isResponseRequired() {
        return isResponseRequired;
    }

    public void setResponseRequired(Boolean responseRequired) {
        isResponseRequired = responseRequired;
    }

    public DataFormat getInputDataFormat() {
        return inputDataFormat;
    }

    public void setInputDataFormat(DataFormat inputDataFormat) {
        this.inputDataFormat = inputDataFormat;
    }

    public ActionType getActionType() {
        return actionType;
    }

    public void setActionType(ActionType actionType) {
        this.actionType = actionType;
    }

    public String getProcedureName() {
        return procedureName;
    }

    public void setProcedureName(String procedureName) {
        this.procedureName = procedureName;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
