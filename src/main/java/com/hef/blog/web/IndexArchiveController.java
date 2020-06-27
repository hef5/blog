package com.hef.blog.web;

import com.hef.blog.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class IndexArchiveController {

    private BlogService blogService;

    @Autowired
    public IndexArchiveController(BlogService blogService) {
        this.blogService = blogService;
    }

    @GetMapping("/archives")
    public String archives(Model model) {
        model.addAttribute("archiveMap", blogService.archiveBlog());
        model.addAttribute("blogCount", blogService.countBlog());
        return "archive";
    }
}

