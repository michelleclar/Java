package org.carl.jooq;

import org.carl.auth.AcmeSecurityIdentity;

public class RequestContext {

    private Integer clientId;

    private Integer langId;

    public RequestContext(Integer clientId, Integer langId) {
        this.clientId = clientId;
        this.langId = langId;
    }

//    public RequestContext(AcmeSecurityIdentity acmeSecurityIdentity, Integer langId) {
//        this.clientId = acmeSecurityIdentity.getClientId();
//        this.langId = langId;
//    }

    public Integer getClientId() {
        return clientId;
    }

    public Integer getLangId() {
        return langId;
    }

}
