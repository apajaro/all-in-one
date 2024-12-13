package com.apajaro.platform.application.input;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Builder
@Data
public class Page<T> {
    @Builder.Default
    private List<T> content = new ArrayList<>();

    @Builder.Default
    private Long totalElements = 0L;

    @Builder.Default
    private Integer totalPages = 0;
}
