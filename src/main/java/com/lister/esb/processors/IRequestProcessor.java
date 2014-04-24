package com.lister.esb.processors;


import com.lister.esb.model.Request;

public interface IRequestProcessor<T> {

    /**
        Required to perform following tasks: <br/>
        1. Check for Integrity of the request (Well formed both for JSON and XML) <br/>
        2. Validate the request. <br/>
        3. Transform the data in to objects of our domain schema. <br/>
        4. Get the action from the request object and then call the spring data with params @ActionType, @ModelObject <br/>
     *
     * @param request
     **/

    String validateAndProcess(Request request);
}
