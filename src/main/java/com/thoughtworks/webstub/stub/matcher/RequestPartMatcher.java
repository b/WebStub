package com.thoughtworks.webstub.stub.matcher;

import com.thoughtworks.webstub.config.HttpConfiguration;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public abstract class RequestPartMatcher {
    protected HttpConfiguration configuration;
    private int failedResponseCode;

    protected RequestPartMatcher(HttpConfiguration configuration, int failedResponseCode) {
        this.configuration = configuration;
        this.failedResponseCode = failedResponseCode;
    }

    public int failedResponseCode() {
        return failedResponseCode;
    }

    public abstract boolean matches(HttpServletRequest request) throws IOException;
}