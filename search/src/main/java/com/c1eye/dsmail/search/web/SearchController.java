package com.c1eye.dsmail.search.web;

import com.c1eye.dsmail.search.service.MailSearchService;
import com.c1eye.dsmail.search.vo.SearchParam;
import com.c1eye.dsmail.search.vo.SearchResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author c1eye
 * time 2022/3/18 16:19
 */
@Controller
public class SearchController {

    @Autowired
    MailSearchService mailSearchService;

    @GetMapping("/list.html")
    public String listPage(SearchParam param, Model model) {
        SearchResult result = mailSearchService.search(param);
        model.addAttribute("result", result);
        return "index";
    }
}
