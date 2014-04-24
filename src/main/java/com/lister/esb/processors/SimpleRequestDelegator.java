package com.lister.esb.processors;

import com.lister.esb.enums.SourceSystem;
import com.lister.esb.model.Request;

import java.util.Map;


public class SimpleRequestDelegator implements IRequestDelegate{


    private Map<SourceSystem, IRequestProcessor> requestProcessorMap;

    public SimpleRequestDelegator() {
    }

    public SimpleRequestDelegator(Map requestProcessorMap) {
        this.requestProcessorMap = requestProcessorMap;
    }

    public String transfer(Request request)  {
        return requestProcessorMap.get(request.getSourceSystem()).validateAndProcess(request);
    }

    public Map<SourceSystem, IRequestProcessor> getRequestProcessorMap() {
        return requestProcessorMap;
    }

    public void setRequestProcessorMap(Map<SourceSystem, IRequestProcessor> requestProcessorMap) {
        this.requestProcessorMap = requestProcessorMap;
    }
}
