package com.apajaro.platform.infrastructure.data.entity;

import com.apajaro.platform.domain.entity.UserLoginPasswordReset;
import com.apajaro.platform.domain.valueobject.ID;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;

@Data
@Builder
@Table(name = "user_login_password_reset")
public class UserLoginPasswordResetDao {
    @Id
    private String id;

    @Column("user_login_id")
    private String userLoginId;

    @Column
    private String token;

    @Column("is_used")
    private Boolean isUsed;

    @Column("valid_until")
    private Instant validUntil;

    @Builder.Default
    @CreatedDate
    @Column("created_at")
    private Instant createdAt = Instant.now();;

    @Builder.Default
    @LastModifiedDate
    @Column("updated_at")
    private Instant updatedAt = Instant.now();

    public UserLoginPasswordReset toDomain() {
        return UserLoginPasswordReset.builder()
                .id(ID.of(this.id))
                .userLoginId(ID.of(this.userLoginId))
                .token(ID.of(this.token))
                .isUsed(this.isUsed)
                .validUntil(this.validUntil)
                .build();
    }

    public static UserLoginPasswordResetDao fromDomain(UserLoginPasswordReset userLoginPasswordReset) {
        return UserLoginPasswordResetDao
                .builder()
                .id(userLoginPasswordReset.getId().getValue())
                .userLoginId(userLoginPasswordReset.getUserLoginId().getValue())
                .token(userLoginPasswordReset.getToken().getValue())
                .isUsed(userLoginPasswordReset.getIsUsed())
                .validUntil(userLoginPasswordReset.getValidUntil())
                .build();
    }
}
