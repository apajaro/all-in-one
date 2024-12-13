package com.apajaro.platform.infrastructure.data.entity;

import com.apajaro.platform.domain.entity.TelecomNumber;
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
@Table(name = "telecom_number")
public class TelecomNumberDao {
    @Id
    @Column("contact_mech_id")
    private String contactMechId;

    @Column("country_code")
    private String countryCode;

    @Column("area_code")
    private String areaCode;

    @Column("contact_number")
    private String contactNumber;

    @Builder.Default
    @CreatedDate
    @Column("created_at")
    private Instant createdAt = Instant.now();;

    @Builder.Default
    @LastModifiedDate
    @Column("updated_at")
    private Instant updatedAt = Instant.now();

    public static TelecomNumberDao fromDomain(TelecomNumber telecomNumber) {
        return TelecomNumberDao.builder()
                .contactMechId(telecomNumber.getId().getValue())
                .countryCode(telecomNumber.getCountryCode())
                .areaCode(telecomNumber.getAreaCode())
                .contactNumber(telecomNumber.getContactNumber())
                .build();
    }

    public TelecomNumber toDomain() {
        return TelecomNumber.builder()
                .id(ID.of(this.contactMechId))
                .countryCode(this.countryCode)
                .areaCode(this.areaCode)
                .contactNumber(this.contactNumber)
                .build();
    }
}
