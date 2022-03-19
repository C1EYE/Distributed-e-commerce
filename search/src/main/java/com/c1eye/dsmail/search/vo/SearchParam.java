package com.c1eye.dsmail.search.vo;

import lombok.Data;

import java.util.List;

/**
 * @author c1eye
 * time 2022/3/18 16:36
 */
@Data
public class SearchParam {
    // 匹配关键词
    private String keyword;
    // 三级分类
    private Long catalog3Id;
    /**
     * 排序
     */
    private String sort;
    // 是否有货
    private Integer hasStock;
    private String skuPrice;
    private List<Long> brandId;
    private List<String> attrs;
    private Integer pageNum;

    /**
     * 原生的所有查询条件
     */
    private String _queryString;


}
