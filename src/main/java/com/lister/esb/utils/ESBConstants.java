package com.lister.esb.utils;

public interface ESBConstants {

    String EX_JSON_FMT = " JSON FORMAT EXCEPTION ";
    String EX_XML_FMT = " XML FORMAT EXCEPTION ";
    String EX_JSON_UNR_PROP = " UNRECOGNIZED PROPERTY EXCEPTION ";
    String EX_JSON_BINDING = " JSON BINDING VALIDATION ERRORS ";
    String EX_ACTION_TYPE_NOT_AVL = " ACTION TYPE ( CREATE/UPDATE ) IS REQUIRED FOR THIS TYPE OF REQUEST ";
    String EX_RESPONSE_JSON_FMT = " RESPONSE JSON FORMAT EXCEPTION ";
    String EX_RESPONSE_XML_FMT = " RESPONSE XML FORMAT EXCEPTION ";
    String RESPONSE_SUCCESS = " SUCCESS ";
    String JPA_REQUEST="NA";
    String EX_DATA_ACCESS="ERROR IN JDBC CALL PROCESSING";
    String EX_DATA_ACCESS_PROCEDURE="ERROR IN PROCESSING ONE OR MANY RECORDS, PLEASE CHECK ERROR QUEUE";
    String EX_RESPONSE_BUILDING="";
}
