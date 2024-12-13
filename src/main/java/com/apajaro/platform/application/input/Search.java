package com.apajaro.platform.application.input;

import lombok.Builder;
import lombok.Data;

import java.util.Optional;

@Builder
@Data
public class Search {
    @Builder.Default
    private String query = "";

    @Builder.Default
    private Pagination pagination = Pagination.builder().build();

    public static Search of(String query, Integer page, Integer size, String[] sort) {
        return of(query, page, size, sort, "asc");
    }

    public static Search of(String query, Integer page, Integer size, String[] sort, String direction) {
        Pagination pagination = Pagination.builder().build();
        Optional.ofNullable(page).ifPresent(pagination::setPage);
        Optional.ofNullable(size).ifPresent(pagination::setSize);
        Optional.ofNullable(sort).ifPresent(pagination::setSort);
        Optional.ofNullable(direction).ifPresent(pagination::setDirection);

        Search search = Search.builder()
                .pagination(pagination)
                .build();

        Optional.ofNullable(query).ifPresent(search::setQuery);
        return search;
    }

    public Integer getTotalPages(long totalElements) {
        return (int) totalElements / (pagination.getSize());
    }
}
