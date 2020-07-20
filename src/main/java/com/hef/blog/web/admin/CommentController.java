package com.hef.blog.web.admin;

import com.hef.blog.entity.Comment;
import com.hef.blog.entity.User;
import com.hef.blog.service.BlogService;
import com.hef.blog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.text.MessageFormat;
import java.util.concurrent.ThreadLocalRandom;

@Controller
@RequestMapping
public class CommentController {

    private CommentService commentService;
    private BlogService blogService;

    @Autowired
    public CommentController(CommentService commentService, BlogService blogService) {
        this.commentService = commentService;
        this.blogService = blogService;
    }

/*    @Value("${comment.avatar}")
    private String avatar;*/

    @GetMapping("/comments/{blogId}")
    public String comments(@PathVariable Long blogId, Model model) {
        model.addAttribute("comments", commentService.getCommentByBlogId(blogId));
        return "blog :: commentList";
    }


    @PostMapping("/comments")
    public String post(Comment comment, HttpSession session) {
        Long blogId = comment.getBlog().getId();
        comment.setBlog(blogService.getBlog(blogId));
        User user = (User) session.getAttribute("user");
        if (user != null) {
            comment.setAvatar(user.getAvatar());
            comment.setAdminComment(true);
        } else {
            long randomNum = ThreadLocalRandom.current().nextInt(1, 2000);
            String url = "https://picsum.photos/id/{0}/100/100";
            String avatar = MessageFormat.format(url, Long.toString(randomNum));
            comment.setAvatar(avatar);
        }
        System.out.println("inside of the  @PostMapping(\"/comments\")");
        commentService.saveComment(comment);
        return "redirect:/comments/" + blogId;
    }


}
