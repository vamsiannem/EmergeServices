package com.lister.esb.dto;

import org.springmodules.validation.bean.conf.loader.annotation.handler.NotNull;

/**
 * Customer related details.
 *
 * @author sudharsan_s
 *
 */
public class ResetPasswordDTO extends BaseDTO{

    @NotNull
	private String userName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
