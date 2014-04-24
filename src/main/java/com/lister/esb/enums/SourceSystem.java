package com.lister.esb.enums;

import com.lister.esb.model.ServiceRequest;
import com.lister.esb.processors.EMarketRequestProcessor;
import com.lister.esb.processors.EServicesRequestProcessor;
import com.lister.esb.processors.IRequestProcessor;

public enum SourceSystem {

    COMMERCE("ECOMM", "E-COMMERCE", ServiceRequest.class),
    MARKET("EMRKT","E-MARKETING", EMarketRequestProcessor.class),
    SERVICES("ESRVC","E-SERVICES", EServicesRequestProcessor.class);

    private String code;
    private String description;
    private Class<IRequestProcessor> classType;

    SourceSystem(String code, String description, Class className) {
        this.code = code;
        this.description = description;
        this.classType = className;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public Class<IRequestProcessor> getClassType() {
        return classType;
    }
}
