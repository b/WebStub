package com.thoughtworks.webstub.dsl;

import com.thoughtworks.webstub.config.StubConfiguration;

public abstract class DslProvider {
    private Request request;

    public DslProvider get(String uri) {
        request = new Request("GET", uri);
        return this;
    }

    public void returns(ResponseBuilder responseBuilder) {
        Response response = responseBuilder.build();
        configurationCreated(
                new StubConfiguration(request.method(), request.uri(), response.status()));
    }

    abstract protected void configurationCreated(StubConfiguration configuration);
}