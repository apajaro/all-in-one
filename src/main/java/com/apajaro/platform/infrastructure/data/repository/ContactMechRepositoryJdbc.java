package com.apajaro.platform.infrastructure.data.repository;

import com.apajaro.platform.infrastructure.data.dto.ContactMechDTO;
import com.apajaro.platform.infrastructure.data.entity.ContactMechDao;
import com.apajaro.platform.infrastructure.data.entity.PostalAddressDao;
import com.apajaro.platform.infrastructure.data.entity.TelecomNumberDao;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ContactMechRepositoryJdbc extends ListCrudRepository<ContactMechDao, String> {


    @Query("SELECT cm.id, pcm.contact_mech_purpose cm.contact_mech_type, cm.info_string, tn.countrycode, tn.areacode, tn.contactnumber, pa.address1, pa.address2, pa.city, pa.state, pa.country, pa.postalcode FROM person_contact_mech pcm JOIN contact_mech cm ON cm.id = pcm.contact_mech_id LEFT JOIN telecom_number tn on tn.contact_mech_id = pcm.contact_mech_id LEFT JOIN postal_address pa on pa.contact_mech_id = pcm.contact_mech_id WHERE (NOW() >= pcm.from_date AND pcm.thru_date IS NULL OR NOW() <= pcm.thru_date ) AND pcm.person_id = :personId")
    List<ContactMechDTO> findAllForPerson(@Param("personId") String personId);

    @Query("SELECT tn.* FROM person_contact_mech pcm JOIN contact_mech cm ON cm.id = pcm.contact_mech_id LEFT JOIN telecom_number tn ON tn.contact_mech_id = pcm.contact_mech_id WHERE (NOW() >= pcm.from_date AND pcm.thru_date IS NULL OR NOW() <= pcm.thru_date ) AND cm.contact_mech_type = 'TELECOM_NUMBER' AND pcm.person_id = :personId AND cm.contact_mech_purpose = :contactMechPurpose")
    Optional<TelecomNumberDao> findTelecomNumber(@Param("personId") String personId, @Param("contactMechPurpose") String contactMechPurpose);

    @Query("SELECT pa.* FROM person_contact_mech pcm JOIN contact_mech cm ON cm.id = pcm.contact_mech_id LEFT JOIN postal_address pa ON pa.contact_mech_id = pcm.contact_mech_id WHERE (NOW() >= pcm.from_date AND pcm.thru_date IS NULL OR NOW() <= pcm.thru_date ) AND cm.contact_mech_type = 'POSTAL_ADDRESS' AND pcm.person_id = :personId AND cm.contact_mech_purpose = :contactMechPurpose")
    Optional<PostalAddressDao> findPostalAddress(@Param("personId") String personId, @Param("contactMechPurpose") String contactMechPurpose);
}
