package org.surveymonkey.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.surveymonkey.models.EndUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.surveymonkey.services.iservices.IEndUserService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Controller
public class EndUserController extends ApplicationController {

    private static final Logger LOG = LoggerFactory.getLogger(EndUserController.class);

    @Autowired
    private IEndUserService endUserService;


    private final String TOPIC = "EndUser";

    @PostMapping(value = "/index/user/success")
    public String createUserConfirmed(@RequestParam String name) {
        if (endUserService.findByName(name) != null) {
            String errorInfo = "User \"" + name + "\" already exists";
            LOG.error(errorInfo);
            return "redirect:/error/" + errorInfo;
        }
        EndUser endUser = new EndUser();
        endUser.setName(name);
        endUserService.save(endUser);

        return "redirect:/index/logon";
    }

    @PostMapping(value = "/user")
    public String logonConfirmed(HttpServletResponse response, @RequestParam String name, Model model) {
        // Add user to model, potentially check if user exists first and send error page if no user?
        if (endUserService.findByName(name) != null) {
            EndUser user = endUserService.findByName(name);
            model.addAttribute("user", user);
            // Use a cookie to store the current user's id
            Cookie cookie = new Cookie("user_id", String.valueOf(user.getEndUserId()));
            cookie.setMaxAge(-1); // Treat as a session cookie
            response.addCookie(cookie); // Add cookie to response

            return "redirect:/";
        }
        String errorInfo = "User \"" + name + "\" does not exist";
        return "redirect:/error/" + errorInfo;
    }

    @GetMapping(value = "/logout")
    public String logout(HttpServletResponse response) {
        Cookie cookie = new Cookie("user_id", null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);

        return "redirect:/";
    }

    @GetMapping(value = "/index/create")
    public String createUser() {
        return "createUser";
    }

    @GetMapping(value = "/index/logon")
    public String logonUser() {
        return "index";
    }

    @GetMapping(value = "/endusercontroller/test")
    @ResponseBody
    public String testEndUserController() {
        return "EndUserController is working";
    }

}