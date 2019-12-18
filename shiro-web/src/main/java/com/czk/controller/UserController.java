package com.czk.controller;

import com.czk.vo.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * @Author: Created by ChenZK
 * @Create: 2019/12/16 16:19
 */
@Controller
public class UserController {

    @RequestMapping(value = "/subLogin", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @ResponseBody
    public String subLogin(User user) {
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(user.getUserName(), user.getPassword());
        try {
            token.setRememberMe(user.isRemenberMe());
            subject.login(token);
        }catch (Exception e) {
            e.printStackTrace();
        }
        if (subject.hasRole("admin")) {
            return "有admin权限";
        }

        return "无admin权限";
    }
    @RequestMapping(value = "cookios", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    @ResponseBody
    public String getCookies(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("rememberme")) {
                return "获取Cookies Success";
            }
        }
        return "获取Cookies Error";
    }

    @RequiresRoles("admin")
    @RequestMapping(value = "testRole", method = RequestMethod.GET)
    @ResponseBody
    public String testRole() {
        return "testRole Success";
    }

    @RequiresRoles("admin1")
    @RequestMapping(value = "testRole1", method = RequestMethod.GET)
    @ResponseBody
    public String testRole1() {
        return "testRole1 Success";
    }
}
