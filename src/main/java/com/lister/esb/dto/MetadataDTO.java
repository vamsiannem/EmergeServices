package com.lister.esb.dto;

import org.springmodules.validation.bean.conf.loader.annotation.handler.Length;
import org.springmodules.validation.bean.conf.loader.annotation.handler.NotNull;
/**
 * Created by IntelliJ IDEA.
 * User: rajeev_m
 * Date: 1/16/13
 * Time: 10:45 AM
 * To change this template use File | Settings | File Templates.
 */
public class MetadataDTO extends BaseDTO {

    private String codeType;

    private String subCodeType;

    public String getCodeType() {
        return codeType;
    }

    public void setCodeType(String codeType) {
        this.codeType = codeType;
    }

    public String getSubCodeType() {
        return subCodeType;
    }

    public void setSubCodeType(String subCodeType) {
        this.subCodeType = subCodeType;
    }
}
