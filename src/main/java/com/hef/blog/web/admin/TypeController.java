package com.hef.blog.web.admin;

import com.hef.blog.entity.Type;
import com.hef.blog.service.TypeService;
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
public class TypeController {

    private final static String REDIRECT_TYPE = "redirect:/admin/types/0";

    private TypeService typeService;

    @Autowired
    public TypeController(TypeService typeService) {
        this.typeService = typeService;
    }

    @GetMapping("/types/{pageNumber}")
    public String list(@PathVariable int pageNumber, Model model){
        Pageable pageable = PageRequest.of(pageNumber, 5, Sort.by("id").descending());
        model.addAttribute("page", typeService.listType(pageable));
        return "admin/types";
    }

    @GetMapping("/types/update")
    public String input(Model model){
        model.addAttribute("type", new Type());
        return "admin/type-input";
    }

    @GetMapping("/types/{id}/update")
    public String editInput(@PathVariable Long id, Model model){
        model.addAttribute("type", typeService.getType(id));
        return "admin/type-input";
    }

    @PostMapping("/types")
    public String post(@Valid Type type, BindingResult result, RedirectAttributes attributes){
        Type type1 = typeService.getTypeByName(type.getName());
        if (type1 != null){
            result.rejectValue("name", "nameError", "该分类已存在");
        }

        if (result.hasErrors()){
            return "admin/type-input";
        }

        Type t = typeService.saveType(type);
        if (t == null ){
            attributes.addFlashAttribute("message", "操作失败！");
        } else {
            attributes.addFlashAttribute("message", "操作成功！");
        }
        return REDIRECT_TYPE;
    }

    @PostMapping("/types/{id}")
    public String editPost(@Valid Type type, BindingResult result,
                           @PathVariable Long id, RedirectAttributes attributes){
        Type type1 = typeService.getTypeByName(type.getName());
        if (type1 != null){
            result.rejectValue("name", "nameError", "该分类已存在");
        }

        if (result.hasErrors()){
            return "admin/type-input";
        }
        Type t = typeService.updateType(id, type);
        if (t == null ){
            attributes.addFlashAttribute("message", "更新失败！");
        } else {
            attributes.addFlashAttribute("message", "更新成功！");
        }
        return REDIRECT_TYPE;
    }

    @GetMapping("/types/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes attributes){
        typeService.deleteType(id);
        attributes.addFlashAttribute("message", "删除成功");
        return REDIRECT_TYPE;
    }




}

