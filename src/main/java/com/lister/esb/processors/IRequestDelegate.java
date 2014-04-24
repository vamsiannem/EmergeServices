package com.lister.esb.processors;

import com.lister.esb.model.Request;

public interface IRequestDelegate {

    public String transfer(Request request);
}
