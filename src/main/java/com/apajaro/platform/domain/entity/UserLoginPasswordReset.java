package com.apajaro.platform.domain.entity;

import com.apajaro.platform.domain.valueobject.ID;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public class UserLoginPasswordReset {
    ID id;
    ID token;
    Boolean isUsed;
    Instant validUntil;
    ID userLoginId;

    public boolean isValid() {
        return !isUsed && validUntil.isAfter(Instant.now());
    }
    public static UserLoginPasswordReset createForUser(ID userLoginId, Long validForSeconds) {
        return UserLoginPasswordReset.builder()
                .id(ID.generate())
                .token(ID.generate())
                .userLoginId(userLoginId)
                .isUsed(false)
                .validUntil(Instant.now().plusSeconds(validForSeconds))
                .build();
    }
}
