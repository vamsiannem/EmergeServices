package com.lister.esb.utils;

import com.lister.esb.enums.ActionType;
import com.lister.esb.enums.SourceSystem;
import com.lister.esb.model.DataFormat;
import com.lister.esb.model.ServiceRequest;
import org.springframework.stereotype.Component;

@Component("serviceRequestFactoryBean")
public class ServiceRequestFactoryBean  {
    public ServiceRequest createServiceRequest(String inputMessage, ActionType actionType, boolean responseRequired, DataFormat inputDataFormat, Class dtoType, SourceSystem sourceSystem, String procedureName)
    {
        ServiceRequest serviceRequest = new ServiceRequest(inputMessage, actionType, responseRequired, inputDataFormat, dtoType ,sourceSystem, procedureName);
        return serviceRequest;
    }
}
