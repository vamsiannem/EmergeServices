package com.lister.esb.dto;

import org.springmodules.validation.bean.conf.loader.annotation.handler.Length;
import org.springmodules.validation.bean.conf.loader.annotation.handler.NotNull;

import java.util.Date;
/**
 * Created with IntelliJ IDEA.
 * User: rajeev
 * Date: 22/5/13
 * Time: 1:29 PM
 * To change this template use File | Settings | File Templates.
 */
public class CampaignProgramDTO extends BaseDTO {

    @NotNull
    private String programId;

    public String getProgramId() {
        return programId;
    }

    public void setProgramId(String programId) {
        this.programId = programId;
    }
}
