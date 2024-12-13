package com.apajaro.platform.domain.entity;

import com.apajaro.platform.domain.valueobject.ID;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserLoginToken {
    ID id;
    String token;
    Boolean isExpired;
    ID userLoginId;

    public void expire() {
        this.isExpired = false;
    }
}
