package com.apajaro.platform.infrastructure.web.controller.treasury;

import com.apajaro.platform.application.input.Page;
import com.apajaro.platform.infrastructure.web.CurrentUser;
import com.apajaro.platform.infrastructure.web.UserPrincipal;
import com.apajaro.platform.infrastructure.web.controller.treasury.dto.VoucherDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/treasury/vouchers")
public interface VoucherResource {
    @GetMapping
    @PreAuthorize("hasAnyAuthority('vouchers.write')")
    Page<VoucherDto> getVouchers(
            @RequestParam(required = false) String query,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size,
            @RequestParam(required = false) String[] sort,
            @RequestParam(required = false) String direction,
            @CurrentUser UserPrincipal userPrincipal
    );

    @PostMapping
    @PreAuthorize("hasAnyAuthority('vouchers.write')")
    VoucherDto createVoucher(@Valid @RequestBody VoucherDto voucherDto, @CurrentUser UserPrincipal userPrincipal);

    @GetMapping("/next-number")
    @PreAuthorize("hasAnyAuthority('vouchers.write')")
    Long getNextVoucherNumber(@Valid @NotNull @NotBlank @RequestParam String voucherType, @CurrentUser UserPrincipal userPrincipal);

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('vouchers.write')")
    void deleteVoucher(@PathVariable Long id, @CurrentUser UserPrincipal userPrincipal);
}
