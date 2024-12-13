package com.apajaro.platform.infrastructure.data.repository;

import com.apajaro.platform.application.repository.VoucherSequenceRepository;
import com.apajaro.platform.domain.enums.VoucherType;
import com.apajaro.platform.domain.valueobject.ID;
import com.apajaro.platform.infrastructure.data.entity.VoucherSequenceDao;
import lombok.AllArgsConstructor;
import org.springframework.data.jdbc.core.JdbcAggregateTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@AllArgsConstructor
public class VoucherSequenceRepositoryImpl implements VoucherSequenceRepository {
    private final VoucherSequenceRepositoryJdbc repository;
    private final JdbcAggregateTemplate template;

    @Override
    public void startSequence(VoucherType voucherType, String organizationId) {
        Optional<VoucherSequenceDao> sequenceDao = repository.findByVoucherTypeAndOrganizationId(voucherType.name(), organizationId);

        if (sequenceDao.isPresent()) {
            return;
        }

        VoucherSequenceDao voucherSequenceDao = VoucherSequenceDao.builder()
                .id(ID.generate().getValue())
                .voucherType(voucherType.name())
                .organizationId(organizationId)
                .sequenceNumber(1L)
                .build();

        template.insert(voucherSequenceDao);
    }

    @Override
    public Long getNextVoucherNumber(VoucherType voucherType, String organizationId) {
        Optional<VoucherSequenceDao> sequenceDao = repository.findByVoucherTypeAndOrganizationId(voucherType.name(), organizationId);

        return sequenceDao.map(VoucherSequenceDao::getSequenceNumber).orElse(0L);
    }

    @Override
    public void updateNextVoucherNumber(VoucherType voucherType, String organizationId, Long nextVoucherNumber) {
        Optional<VoucherSequenceDao> sequenceDao = repository.findByVoucherTypeAndOrganizationId(voucherType.name(), organizationId);
        if (sequenceDao.isEmpty()) {
            return;
        }

        VoucherSequenceDao voucherSequenceDao = sequenceDao.get();
        voucherSequenceDao.setSequenceNumber(nextVoucherNumber);
        template.update(voucherSequenceDao);
    }
}
