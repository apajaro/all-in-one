package com.apajaro.platform.domain.entity;

import com.apajaro.platform.domain.valueobject.VoucherAmount;
import lombok.Builder;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Builder
@Data
public class VoucherDetail {
    private String description;
    private VoucherAmount amount;
    @Builder.Default
    private Set<String> contentUrls = new HashSet<>();

    public void addContent(String contentUrl) {
        this.contentUrls.add(contentUrl);
    }
}
