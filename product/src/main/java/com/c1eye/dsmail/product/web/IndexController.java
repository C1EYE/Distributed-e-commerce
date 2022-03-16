package com.c1eye.dsmail.product.web;

import com.c1eye.dsmail.product.entity.CategoryEntity;
import com.c1eye.dsmail.product.service.CategoryService;
import com.c1eye.dsmail.product.vo.Catelog2Vo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * @author c1eye
 * time 2022/3/16 13:38
 */
@Controller
public class IndexController {


    @Autowired
    private CategoryService categoryService;

    @GetMapping({"/","/index.html"})
    public String indexPage(Model model){
        List<CategoryEntity> categoryEntityList = categoryService.getLevelOneCategorys();
        model.addAttribute("categorys", categoryEntityList);
        return "index";
    }

    @ResponseBody
    @GetMapping("/index/catalog.json")
    public Map<String, List<Catelog2Vo>> getCatalogJson(){
        Map<String, List<Catelog2Vo>> res =   categoryService.getCatalogJson();
        return res;
    }
}
