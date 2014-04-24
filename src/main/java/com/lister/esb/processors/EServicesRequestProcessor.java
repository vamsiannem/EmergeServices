package com.lister.esb.processors;

import com.lister.esb.dto.BaseDTO;
import com.lister.esb.model.BaseBO;
import com.lister.esb.model.Request;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("eServicesRP")
public class EServicesRequestProcessor extends BaseProcessor {

    private Logger logger = Logger.getLogger(EServicesRequestProcessor.class);


    public String validateAndProcess(Request request) {

        String responseString = null;
        if ( request.getProcedureName() != null ) {
            responseString = processJDBCRequest(request);
        }
        else {
            responseString = processJpaRequest(request);
        }

        return responseString;
    }

    private String processJpaRequest(Request request) {
        String responseString = null;
        Class<? extends BaseDTO> _dtoClass = request.getDtoClass();
        Class<? extends BaseBO> _boClass = getBOClass(_dtoClass);
        List<BaseDTO> baseDTOs = null;
        List<BaseBO> baseBOs = null;
        logger.info("Input Request:"+ request.toString());
        logger.info("BO Class:"+ _boClass);
        // 1. JSON to Dto Conversion
        logger.debug("Converting Json/XML to DTO");
        baseDTOs = getDTOList(request.getInputMessage(), request.getInputDataFormat(), _dtoClass);

        // 2. Attribute Validations
        logger.debug("Validating DTO using BeanValidator (annotation based)");
        if( baseDTOs != null ){
            doValidation(request.getInputMessage(), baseDTOs, _dtoClass);
            logger.debug("Converting DTO input to BO to process the request");
            baseBOs = getConversionUtils().convertToBOFromDTO(baseDTOs);  // Get BO from DTO
            logger.debug("CRUDing BO to/from the DB");
            processJpaCall(request, baseBOs, _boClass);     // Process request based on ActionType (CRUD)
            responseString = getResponse(baseBOs, _boClass, request);  // Get the Json/XML response from the output of previous step.
        }
        return responseString;
    }

    private String processJDBCRequest(Request request) {
        Object response = processJDBCCall(request);
        return response.toString();
    }
}
