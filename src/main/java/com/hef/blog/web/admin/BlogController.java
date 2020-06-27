package com.hef.blog.web.admin;

import com.hef.blog.entity.Blog;
import com.hef.blog.entity.Tag;
import com.hef.blog.entity.User;
import com.hef.blog.service.BlogService;
import com.hef.blog.service.TagService;
import com.hef.blog.service.TypeService;
import com.hef.blog.vo.BlogQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class BlogController {

    private static final String INPUT = "admin/blog-input";
    private static final String LIST = "admin/blogs";
    private static final String REDIRECT_LIST = "redirect:/admin/blogs";
    private static final String LIST_SEARCH = "admin/blogs :: blogList";

    final private BlogService blogService;
    final private TypeService typeService;
    final private TagService tagService;

    @Autowired
    public BlogController(BlogService blogService, TypeService typeService, TagService tagService) {
        this.blogService = blogService;
        this.typeService = typeService;
        this.tagService = tagService;
    }

    // retrieve blogs, types and render them in blogs.html
    @GetMapping("/blogs")
    public String blogs(@PageableDefault(size=2, sort ={"updateTime"}, direction = Sort.Direction.DESC)
                                    Pageable pageable, Model model){
        model.addAttribute("types", typeService.listType());
        model.addAttribute("page", blogService.listBlog(pageable));
        return LIST;
    }

    // list blogs under the search condition of the given title and type.
    @PostMapping("/blogs/search")
    public String search(@PageableDefault(size=2, sort ={"updateTime"}, direction = Sort.Direction.DESC)
                                     Pageable pageable, BlogQuery blog, Model model){
        model.addAttribute("page", blogService.listBlog(pageable, blog));
        return LIST_SEARCH;
    }

    private void setTypeAndTag(Model model){
        model.addAttribute("types",typeService.listType());
        model.addAttribute("tags", tagService.listTag());
    }

    // create a blog: create a new blog object return blog-input.html
    @GetMapping("/blogs/input")
    public String input(Model model){
        setTypeAndTag(model);
        model.addAttribute("blog", new Blog());
        return INPUT;
    }

    // update a blog: get a blog by id and render in the blog-input.html
    @GetMapping("/blogs/{id}/update")
    public String blogs(@PathVariable Long id, Model model){
        setTypeAndTag(model);
        Blog blog = blogService.getBlog(id);
        blog.init();
        model.addAttribute("blog", blogService.getBlog(id));
        return INPUT;
    }

    // save the blog from blog-input.html
    @PostMapping("/blogs")
    public String post(Blog blog, RedirectAttributes attributes, HttpSession session){
        blog.setUser((User) session.getAttribute("user"));
        blog.setType(typeService.getType(blog.getType().getId()));
        System.out.println(blog.getTagIds());

        blog.setTags(tagService.listTag(blog.getTagIds()));

        Blog blog1;
        // check if the blog is created
        if (blog.getId()==null) {
            blog1 = blogService.saveBlog(blog);
        } else {
            blog1 = blogService.updateBlog(blog.getId(), blog);
        }

        if ( blog1 == null ){
            attributes.addFlashAttribute("message", "操作失败");
        }else {
            attributes.addFlashAttribute("message", "操作成功");
        }
        return REDIRECT_LIST;
    }

    // delete a blog
    @GetMapping("/blogs/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes attributes){
        blogService.deleteBlog(id);
        attributes.addFlashAttribute("message", "删除成功");
        return REDIRECT_LIST;
    }




}
