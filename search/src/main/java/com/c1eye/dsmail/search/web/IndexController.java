package com.c1eye.dsmail.search.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author c1eye
 * time 2022/3/18 15:35
 */
@Controller
public class IndexController {

    @GetMapping({"/","/index.html"})
    public String indexPage(){
        return "index";
    }
}
