package com.hef.blog.web.admin;

import com.hef.blog.entity.User;
import com.hef.blog.service.UserService;
import com.hef.blog.util.JwtTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class LoginController {

    private static final String LOGIN_SUCCESS = "admin/login-success";
    private static final String LOGIN = "admin/login";
    private static final String REDIRECT_ADMIN = "redirect:/admin";

    private UserService userService;

    @Autowired
    public LoginController(UserService userService){
        this.userService = userService;
    }

    @GetMapping({"", "/login"})
    public String loginPage(){
        return LOGIN;
    }

    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        RedirectAttributes attributes,
                        Model model){

        User user = userService.checkUser(username, password);

        if (user != null){

            user.setPassword(null);
            /* session.setAttribute("user", user); */

            // if username password correct, then generate token and set response header
            String token = JwtTokenUtils.generateToken(username);

            model.addAttribute("user", user);
            model.addAttribute("token", token);

            return LOGIN_SUCCESS;
        } else {
            attributes.addFlashAttribute("message", "用户名和密码错误");
            return REDIRECT_ADMIN;
        }
    }

    @GetMapping("logout")
    public String logout(HttpSession session){
        /*session.removeAttribute("user");*/
        return REDIRECT_ADMIN;
    }



}
