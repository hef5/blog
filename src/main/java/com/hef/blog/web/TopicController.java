package com.hef.blog.web;

import com.hef.blog.entity.Tag;
import com.hef.blog.service.BlogService;
import com.hef.blog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class TopicController {


    private TagService tagService;
    private BlogService blogService;

    @Autowired
    public TopicController(TagService tagService, BlogService blogService) {
        this.tagService = tagService;
        this.blogService = blogService;
    }

    @GetMapping("/topics")
    public String tags(@PageableDefault(size = 5, sort = {"updateTime"},
            direction = Sort.Direction.DESC) Pageable pageable, Model model){

        List<Tag> tags = tagService.listTagTop(1000);
        model.addAttribute("tags", tags);
        model.addAttribute("page", blogService.listBlog(pageable));
        model.addAttribute("activeTagId", -1L);
        return "topics";
    }

    @GetMapping("/topics/{id}")
    public String tags(@PageableDefault(size = 5, sort = {"updateTime"},
            direction = Sort.Direction.DESC) Pageable pageable,
                       @PathVariable Long id, Model model){
        List<Tag> tags = tagService.listTagTop(1000);

        model.addAttribute("tags", tags);
        model.addAttribute("page", blogService.listBlog(id,pageable));
        model.addAttribute("activeTagId", id);
        return "topics";
    }



}
