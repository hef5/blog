package com.hef.blog.web.admin;

import com.hef.blog.entity.Tag;
import com.hef.blog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/admin")
public class TagController {

    private TagService tagService;

    @Autowired
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping("/tags/{pageNumber}")
    public String list(@PathVariable int pageNumber, Model model){
        Pageable pageable = PageRequest.of(pageNumber, 5, Sort.by("id").descending());
        model.addAttribute("page", tagService.listTag(pageable));
        return "admin/tags";
    }

    // create
    @GetMapping("/tags/update")
    public String input(Model model){
        model.addAttribute("tag", new Tag());
        return "admin/tag-input";
    }

    // update
    @GetMapping("/tags/{id}/update")
    public String editInput(@PathVariable Long id, Model model){
        model.addAttribute("tag", tagService.getTag(id));
        return "admin/tag-input";
    }

    @PostMapping("/tags")
    public String post(@Valid Tag tag, BindingResult result, RedirectAttributes attributes){
        Tag tag1 = tagService.getTagByName(tag.getName());
        if (tag1 != null){
            result.rejectValue("name", "nameError", "该分类已存在");
        }

        if (result.hasErrors()){
            return "admin/tag-input";
        }

        Tag t = tagService.saveTag(tag);
        if (t == null ){
            attributes.addFlashAttribute("message", "操作失败！");
        } else {
            attributes.addFlashAttribute("message", "操作成功！");
        }
        return "redirect:/admin/tags/0";
    }

    @PostMapping("/tags/{id}")
    public String editPost(@Valid Tag tag, BindingResult result,
                           @PathVariable Long id, RedirectAttributes attributes){
        System.out.println("tag name: " + tag.getName());
        Tag tag1 = tagService.getTagByName(tag.getName());
        System.out.println(tag1);
        System.out.println("tag1 name: " + tag1.getName());

        if (tag1 != null && tag1.getName().equals(tag.getName())){
            result.rejectValue("name", "nameError", "该分类已存在");
        }

        if (result.hasErrors()){
            return "admin/tag-input";
        }
        Tag t = tagService.updateTag(id, tag);
        if (t == null ){
            attributes.addFlashAttribute("message", "更新失败！");
        } else {
            attributes.addFlashAttribute("message", "更新成功！");
        }
        return "redirect:/admin/tags";
    }

    @GetMapping("/tags/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes attributes){
        tagService.deleteTag(id);
        attributes.addFlashAttribute("message", "删除成功");
        return "redirect:/admin/tags";
    }




}
