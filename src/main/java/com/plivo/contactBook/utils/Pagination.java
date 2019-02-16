package com.plivo.contactBook.utils;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Optional;

public class Pagination {
    public static Pageable getPageable(Optional<Integer> limit, Optional<Integer> page, Optional<String> sortBy, Optional<String> order){
        int pageNumber, pageSize;
        if (!limit.isPresent() || limit.get() < 1) {
            pageSize = 10;
        } else {
            pageSize = limit.get();
        }
        if (!page.isPresent() || page.get() < 1) {
            pageNumber = 0;
        } else {
            pageNumber = page.get() - 1;
        }
        Pageable pageable;
        if (sortBy.isPresent()) {
            if (order.isPresent() && order.get().equals(Constants.Sort.ASCENDING)) {
                pageable = PageRequest.of(pageNumber, pageSize,
                        Sort.by(sortBy.get()).ascending());
            } else {
                pageable = PageRequest.of(pageNumber, pageSize,
                        Sort.by(sortBy.get()).descending());
            }
        } else {
            pageable = PageRequest.of(pageNumber, pageSize);
        }
        return pageable;
    }

}
