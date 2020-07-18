package com.hef.blog.web;

import com.hef.blog.service.BlogService;
import com.hef.blog.service.TagService;
import com.hef.blog.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {

    private BlogService blogService;
    private TypeService typeService;
    private TagService tagService;

    @Autowired
    public HomeController(BlogService blogService, TypeService typeService, TagService tagService) {
        this.blogService = blogService;
        this.typeService = typeService;
        this.tagService = tagService;
    }

    @GetMapping({"/",""})
    public String index(@PageableDefault(size=10, sort ={"updateTime"}, direction = Sort.Direction.DESC)
                                    Pageable pageable, Model model){
        model.addAttribute("page", blogService.listBlog(pageable));
        model.addAttribute("tags", tagService.listTagTop(10));
        System.out.println("----- index -----");
        return "home";
    }

    @PostMapping("/search")
    public String search(@PageableDefault(size=10, sort ={"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                         @RequestParam String query, Model model){
        model.addAttribute("page", blogService.listBlog("%"+query+"%", pageable));
        model.addAttribute("query", query);
        return "search";
    }

    @GetMapping("/blog/{id}")
    public String blog(@PathVariable Long id, Model model){
        model.addAttribute("blog", blogService.getAndConvert(id));
        return "blog";
    }




}
