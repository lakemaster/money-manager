package com.jojobi.mm.session;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Optional;

@Component
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS, value = "session")
public class SessionParameters implements Serializable {

    private Long myAccountId;

    public Optional<Long> getMyAccountId() {
        return Optional.ofNullable(myAccountId);
    }

    public void setMyAccountId(Long myAccountId) {
        this.myAccountId = myAccountId;
    }
}
