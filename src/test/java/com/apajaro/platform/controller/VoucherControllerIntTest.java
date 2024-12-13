package com.apajaro.platform.controller;

import com.apajaro.platform.common.ControllerIntTest;

class VoucherControllerIntTest extends ControllerIntTest {

    /*@Test
    void getNextSequenceNumberFailsIfVoucherTypeIsNotProvided() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/accounting/vouchers/next-number")
                        .contentType("application/json"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testGetNextSequenceNumber() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/accounting/vouchers/next-number?voucherType=CREDIT")
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(content().string("6"));
    }

    @Test
    @DisplayName("Get all vouchers without filter search")
    void testGetAllVouchersWithoutFilterSearch() throws Exception {
        Pagination pagination = Pagination.builder().build();
        Search search = Search.builder()
                .pagination(pagination)
                .build();

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/accounting/vouchers")
                        .contentType("application/json")
                        .content(asJsonString(search)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.totalElements").value(9))
                .andExpect(jsonPath("$.totalPages").value(1));
    }

    @Test
    @DisplayName("Get all vouchers with filter search")
    void testGetAllVouchersWithFilterSearch() throws Exception {
        Pagination pagination = Pagination.builder().build();
        Search search = Search.builder()
                .pagination(pagination)
                .query("Concept2")
                .build();

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/accounting/vouchers")
                        .contentType("application/json")
                        .content(asJsonString(search)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.totalElements").value(1))
                .andExpect(jsonPath("$.totalPages").value(1));
    }

    @Test
    @DisplayName("Get all vouchers with pagination")
    void testGetVouchersWithPagination() throws Exception {
        Pagination pagination = Pagination.builder().size(3).build();
        Search search = Search.builder()
                .pagination(pagination)
                .build();

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/accounting/vouchers")
                        .contentType("application/json")
                        .content(asJsonString(search)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.totalElements").value(9))
                .andExpect(jsonPath("$.totalPages").value(3));
    }

    @Test
    @DisplayName("Create voucher works")
    void testCreateVoucher() throws Exception {
        VoucherDetailDto voucherDetailDto = VoucherDetailDto.builder()
                .description("Description")
                .amount(BigDecimal.valueOf(134.0))
                .build();
        VoucherDto voucherDto = VoucherDto.builder()
                .type("CREDIT")
                .city("Test city")
                .voucherDate("2021-09-01")
                .counterParty("Counter party")
                .deliveredBySignatureUrl("https://www.google.com")
                .receivedBySignatureUrl("https://www.google.com")
                .details(Set.of(voucherDetailDto))
                .build();
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/accounting/vouchers")
                        .contentType("application/json")
                        .content(asJsonString(voucherDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.voucherNumber").value(6))
                .andExpect(jsonPath("$.concept").value("Concept"))
                .andExpect(jsonPath("$.type").value("CREDIT"));
    }

    @Test
    @DisplayName("Create voucher fails if voucher type is not provided")
    void testCreateVoucherFailsIfVoucherTypeIsNotProvided() throws Exception {
        VoucherDetailDto voucherDetailDto = VoucherDetailDto.builder()
                .description("Description")
                .amount(BigDecimal.valueOf(134.0))
                .build();
        VoucherDto voucherDto = VoucherDto.builder()
                .city("Test city")
                .voucherDate("2021-09-01")
                .counterParty("Counter party")
                .deliveredBySignatureUrl("https://www.google.com")
                .receivedBySignatureUrl("https://www.google.com")
                .details(Set.of(voucherDetailDto))
                .build();
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/accounting/vouchers")
                        .contentType("application/json")
                        .content(asJsonString(voucherDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Create voucher fails if voucher type is not valid")
    void testCreateVoucherFailsIfVoucherTypeIsNotValid() throws Exception {
        VoucherDetailDto voucherDetailDto = VoucherDetailDto.builder()
                .description("Description")
                .amount(BigDecimal.valueOf(134.0))
                .build();
        VoucherDto voucherDto = VoucherDto.builder()
                .type("INVALID")
                .city("Test city")
                .voucherDate("2021-09-01")
                .counterParty("Counter party")
                .deliveredBySignatureUrl("https://www.google.com")
                .receivedBySignatureUrl("https://www.google.com")
                .details(Set.of(voucherDetailDto))
                .build();
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/accounting/vouchers")
                        .contentType("application/json")
                        .content(asJsonString(voucherDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Create voucher fails if voucher date is not provided")
    void testCreateVoucherFailsIfVoucherDateIsNotProvided() throws Exception {
        VoucherDetailDto voucherDetailDto = VoucherDetailDto.builder()
                .description("Description")
                .amount(BigDecimal.valueOf(134.0))
                .build();
        VoucherDto voucherDto = VoucherDto.builder()
                .type("CREDIT")
                .city("Test city")
                .counterParty("Counter party")
                .deliveredBySignatureUrl("https://www.google.com")
                .receivedBySignatureUrl("https://www.google.com")
                .details(Set.of(voucherDetailDto))
                .build();
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/accounting/vouchers")
                        .contentType("application/json")
                        .content(asJsonString(voucherDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Create voucher fails if details are not provided")
    void testCreateVoucherFailsIfDetailsAreNotProvided() throws Exception {
        VoucherDto voucherDto = VoucherDto.builder()
                .type("CREDIT")
                .city("Test city")
                .voucherDate("2021-09-01")
                .counterParty("Counter party")
                .deliveredBySignatureUrl("https://www.google.com")
                .receivedBySignatureUrl("https://www.google.com")
                .build();
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/accounting/vouchers")
                        .contentType("application/json")
                        .content(asJsonString(voucherDto)))
                .andExpect(status().isBadRequest());
    }*/
}
