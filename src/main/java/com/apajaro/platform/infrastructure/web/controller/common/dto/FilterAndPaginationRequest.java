package com.apajaro.platform.infrastructure.web.controller.common.dto;

import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class FilterAndPaginationRequest {
    String searchTerm;

    @Min(0)
    Integer page = 0;

    @Min(20)
    Integer size = 20;

    String[] sort = new String[0];
}
