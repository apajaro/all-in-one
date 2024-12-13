package com.apajaro.platform.infrastructure.data.entity;

import com.apajaro.platform.domain.entity.PostalAddress;
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
@Table(name = "postal_address")
public class PostalAddressDao {
    @Id
    @Column("contact_mech_id")
    private String contactMechId;

    @Column
    private String address1;

    @Column
    private String address2;

    @Column
    private String city;

    @Column
    private String state;

    @Column
    private String country;

    @Column("postal_code")
    private String postalCode;

    @Builder.Default
    @CreatedDate
    @Column("created_at")
    private Instant createdAt = Instant.now();;

    @Builder.Default
    @LastModifiedDate
    @Column("updated_at")
    private Instant updatedAt = Instant.now();

    public static PostalAddressDao fromDomain(PostalAddress postalAddress) {
        return PostalAddressDao.builder()
                .contactMechId(postalAddress.getId().getValue())
                .address1(postalAddress.getAddress1())
                .address2(postalAddress.getAddress2())
                .city(postalAddress.getCity())
                .state(postalAddress.getState())
                .country(postalAddress.getCountry())
                .postalCode(postalAddress.getPostalCode())
                .build();
    }

    public PostalAddress toDomain() {
        return PostalAddress.builder()
                .id(ID.of(this.contactMechId))
                .address1(this.address1)
                .address2(this.address2)
                .city(this.city)
                .state(this.state)
                .country(this.country)
                .postalCode(this.postalCode)
                .build();
    }
}
