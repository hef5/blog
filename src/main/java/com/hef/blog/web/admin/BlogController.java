package com.hef.blog.web.admin;

import com.hef.blog.entity.Blog;
import com.hef.blog.entity.User;
import com.hef.blog.service.BlogService;
import com.hef.blog.service.TagService;
import com.hef.blog.service.TypeService;
import com.hef.blog.service.UserService;
import com.hef.blog.util.JwtTokenUtils;
import com.hef.blog.vo.BlogQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/admin")
public class BlogController {

    private static final String INPUT = "admin/blog-input";
    private static final String LIST = "admin/blogs";
    private static final String REDIRECT_LIST = "redirect:/admin/blogs/0";
    // private static final String LIST_SEARCH = "admin/blogs :: blogList";

    final private BlogService blogService;
    final private TypeService typeService;
    final private TagService tagService;
    final private UserService userService;


    @Autowired
    public BlogController(BlogService blogService, TypeService typeService, TagService tagService, UserService userService) {
        this.blogService = blogService;
        this.typeService = typeService;
        this.tagService = tagService;
        this.userService = userService;
    }

    /**
     * retrieve blogs, types and render them in blogs.html
     * @param pageNumber
     * @param blog
     * @param model
     * @return
     */
    @GetMapping("/blogs/{pageNumber}")
    public String blogs(@PathVariable int pageNumber,
                        @ModelAttribute("blog") BlogQuery blog,  Model model){
        Pageable pageable = PageRequest.of(pageNumber, 10, Sort.by("updateTime").descending());
        model.addAttribute("page", blogService.listBlog(pageable));
        model.addAttribute("types", typeService.listType());
        return LIST;
    }

    /**
     * list blogs under the search condition of the given title and type.
     * @param pageNumber
     * @param blog
     * @param model
     * @return
     */
    @PostMapping("/blogs/search/{pageNumber}")
    public String search(@PathVariable int pageNumber,
                         @ModelAttribute("blog") BlogQuery blog, Model model){
        Pageable pageable = PageRequest.of(pageNumber, 10, Sort.by("updateTime").descending());
        model.addAttribute("page", blogService.listBlog(pageable, blog));
        model.addAttribute("types", typeService.listType());
        return LIST;
    }

    private void setTypeAndTag(Model model){
        model.addAttribute("types",typeService.listType());
        model.addAttribute("tags", tagService.listTag());
    }

    /**
     * create a blog: create a new blog object return blog-input.html
     * @param model
     * @return
     */
    @GetMapping("/blogs/input")
    public String input(Model model){
        setTypeAndTag(model);
        model.addAttribute("blog", new Blog());
        return INPUT;
    }

    /**
     * update a blog: get a blog by id and render in the blog-input.html
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/blogs/{id}/update")
    public String blogs(@PathVariable Long id, Model model){
        setTypeAndTag(model);
        Blog blog = blogService.getBlog(id);
        blog.init();
        model.addAttribute("blog", blogService.getBlog(id));
        return INPUT;
    }

    /**
     * save the blog from blog-input.html
     * @param blog
     * @param attributes
     * @param request
     * @return
     */
    @PostMapping("/blogs/input")
    public String post(Blog blog, RedirectAttributes attributes, HttpServletRequest request){

        String token = request.getHeader("Authorization");
        String username = JwtTokenUtils.getUsernameByToken(token);
        User user = userService.getUserByUsername(username);

        blog.setUser(user);

        if(blog.getType() != null){
            blog.setType(typeService.getType(blog.getType().getId()));
        }

        System.out.println(blog.getTagIds());
        blog.setTags(tagService.listTag(blog.getTagIds()));

        Blog blog1;
        if (blog.getId()==null) {
            blog1 = blogService.saveBlog(blog); // create a new blog
        } else {
            blog1 = blogService.updateBlog(blog.getId(), blog); // update a blog
        }

        if ( blog1 == null ){
            attributes.addFlashAttribute("message", "操作失败");
        }else {
            attributes.addFlashAttribute("message", "操作成功");
        }
        return REDIRECT_LIST;
    }

    /**
     * delete a blog
     * @param id
     * @param attributes
     * @return
     */
    @GetMapping("/blogs/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes attributes){
        blogService.deleteBlog(id);
        attributes.addFlashAttribute("message", "删除成功");
        return REDIRECT_LIST;
    }




}
