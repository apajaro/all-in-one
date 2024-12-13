package com.apajaro.platform.repository;

import com.apajaro.platform.common.JdbcIntTest;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Sql(scripts = "classpath:db/changelog/v1/test-seed-data.sql")
public class VoucherSequenceRepositoryIntTest extends JdbcIntTest {
    /*@Autowired
    VoucherSequenceRepository voucherSequenceRepository;

    @Test
    @DisplayName("Returns zero if the sequence for this tenant has not started yet")
    void returnsZeroIfTheSequenceForThisTenantHasNotStartedYet() {
        Long nextVoucherNumber = voucherSequenceRepository.getNextVoucherNumber(VoucherType.CREDIT, "bd99503a-409a-4602-afbc-2bcae97cf1e1");
        assertEquals(0L, nextVoucherNumber);
    }

    @Test
    @DisplayName("Returns the actual sequence if exists")
    void returnsTheActualSequenceIfExists() {
        Long nextVoucherNumber = voucherSequenceRepository.getNextVoucherNumber(VoucherType.CREDIT, "c06cf4a7-a0b7-4d3b-85ac-35558ca4ea29");
        assertEquals(3L, nextVoucherNumber);
    }*/
}
