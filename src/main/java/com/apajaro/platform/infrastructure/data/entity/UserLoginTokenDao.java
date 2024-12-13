package com.apajaro.platform.infrastructure.data.entity;

import com.apajaro.platform.domain.entity.UserLoginToken;
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
@Table(name = "user_login_token")
public class UserLoginTokenDao {
    @Id
    private String id;

    @Column("user_login_id")
    private String userLoginId;

    @Column
    private String token;

    @Column("is_expired")
    private Boolean isExpired;

    @Builder.Default
    @CreatedDate
    @Column("created_at")
    private Instant createdAt = Instant.now();;

    @Builder.Default
    @LastModifiedDate
    @Column("updated_at")
    private Instant updatedAt = Instant.now();

    public UserLoginToken toDomain() {
        return UserLoginToken.builder()
                .id(ID.of(this.id))
                .userLoginId(ID.of(this.userLoginId))
                .token(this.token)
                .isExpired(this.isExpired)
                .build();
    }

    public static UserLoginTokenDao fromDomain(UserLoginToken userLoginToken) {
        return UserLoginTokenDao
                .builder()
                .id(userLoginToken.getId().getValue())
                .userLoginId(userLoginToken.getUserLoginId().getValue())
                .token(userLoginToken.getToken())
                .isExpired(userLoginToken.getIsExpired())
                .build();
    }
}
