package com.apajaro.platform.infrastructure.data;

import com.apajaro.platform.application.input.Pagination;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class PaginationUtils {
    public static Pageable toPageable(Pagination pagination) {
        Sort sort = Sort.by(pagination.getSort());
        if (pagination.isAsc()) {
            sort.ascending();
        } else {
            sort.descending();
        }

        return PageRequest.of(pagination.getPage(), pagination.getSize(), sort);
    }
}
