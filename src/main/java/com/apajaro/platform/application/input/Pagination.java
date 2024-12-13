package com.apajaro.platform.application.input;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Pagination {
    public static final String ASC = "ASC";

    @Builder.Default
    private int page = 0;

    @Builder.Default
    private int size = 10;

    @Builder.Default
    private String[] sort = {"created_at"};

    @Builder.Default
    private String direction = ASC;

    public Boolean isAsc() {
        return this.direction.equals(ASC);
    }
}
