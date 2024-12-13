package com.apajaro.platform.service;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.spy;

@ExtendWith(MockitoExtension.class)
public class VoucherServiceTest {
    /*@Mock
    private VoucherRepository voucherRepository;
    @Mock
    private VoucherSequenceRepository voucherSequenceRepository;

    private VoucherService voucherService;

    @BeforeEach
    void setUp() {
        voucherService = spy(new VoucherServiceImpl(voucherRepository, voucherSequenceRepository));
    }

    @Test
    @DisplayName("Service should create a new voucher sequence if it does not exist")
    void serviceShouldCreateANewVoucherSequenceIfItDoesNotExist() {
        Mockito
                .when(voucherSequenceRepository.getNextVoucherNumber(VoucherType.DEBIT, "bd99503a-409a-4602-afbc-2bcae97cf1e1"))
                .thenReturn(0L);


        Long nextVoucherNumber = voucherService.getNextVoucherNumber(VoucherType.DEBIT, "bd99503a-409a-4602-afbc-2bcae97cf1e1");
        Assertions.assertEquals(1L, nextVoucherNumber);
        Mockito
                .verify(voucherSequenceRepository, Mockito.times(1))
                .startSequence(VoucherType.DEBIT, "bd99503a-409a-4602-afbc-2bcae97cf1e1");
    }

    @Test
    @DisplayName("Service should return the next voucher number if the sequence already exists")
    void serviceShouldReturnTheNextVoucherNumberIfTheSequenceAlreadyExists() {
        Mockito
                .when(voucherSequenceRepository.getNextVoucherNumber(VoucherType.DEBIT, "bd99503a-409a-4602-afbc-2bcae97cf1e1"))
                .thenReturn(1L);

        Long nextVoucherNumber = voucherService.getNextVoucherNumber(VoucherType.DEBIT, "bd99503a-409a-4602-afbc-2bcae97cf1e1");
        Assertions.assertEquals(2L, nextVoucherNumber);
        Mockito
                .verify(voucherSequenceRepository, Mockito.times(0))
                .startSequence(VoucherType.DEBIT, "bd99503a-409a-4602-afbc-2bcae97cf1e1");
    }*/
}
