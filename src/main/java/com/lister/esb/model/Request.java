package com.lister.esb.model;


import com.lister.esb.dto.BaseDTO;
import com.lister.esb.enums.ActionType;
import com.lister.esb.enums.SourceSystem;

public interface Request extends Bean{

    String getInputMessage();

    ActionType getActionType();

    DataFormat getInputDataFormat();

    boolean isResponseRequired();

    Class<? extends BaseDTO> getDtoClass();

    SourceSystem getSourceSystem();

    String getProcedureName();









}
