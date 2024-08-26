package com.sideprj.ipoAlarm.util.page;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class PageResponseVo<T> implements Serializable {

    private List<T> content;

    private int pageNumber;

    private int pageSize;

    private Long totalElements;


    private Long totalPages;
}
