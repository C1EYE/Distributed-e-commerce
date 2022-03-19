package com.c1eye.dsmail.search.service;

import com.c1eye.dsmail.search.vo.SearchParam;
import com.c1eye.dsmail.search.vo.SearchResult;

/**
 * @author c1eye
 * time 2022/3/18 16:29
 */
public interface MailSearchService {


    SearchResult search(SearchParam param);
}
