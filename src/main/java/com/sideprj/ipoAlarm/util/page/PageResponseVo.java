package com.sideprj.ipoAlarm.util.page;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public class PageResponseVo<T> implements Serializable {

    private List<T> content;

    private int pageNumber;

    private int pageSize;

    private Long totalElements;


    private Long totalPages;
}
