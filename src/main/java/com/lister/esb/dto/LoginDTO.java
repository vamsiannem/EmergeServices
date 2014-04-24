package com.lister.esb.dto;

import org.springmodules.validation.bean.conf.loader.annotation.handler.NotNull;

import java.util.Date;

/**
 * Customer related details.
 *
 * @author sudharsan_s
 *
 */
public class LoginDTO extends BaseDTO{

    @NotNull
	private String userName;

    private String password;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
