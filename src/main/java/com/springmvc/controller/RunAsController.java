package com.springmvc.controller;

import com.springmvc.service.RunAsService;
import com.springmvc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by amitdubey on 17/02/18.
 */

@Controller
public class RunAsController {
    @Autowired
    RunAsService runAsService;

    @Autowired
    UserService userService;

    @RequestMapping("/runas1")
    @ResponseBody
    @Secured({"ROLE_ADMIN", "RUN_AS_CUSTOM"})
    public String runAs1() {
        return runAsService.display();
    }
    @RequestMapping("/runas2")
    @ResponseBody
    @Secured({"ROLE_ADMIN"})
    public String runAs2() {
        return runAsService.display();
    }


    @RequestMapping("/userInfo")
    @ResponseBody
    @Secured({"ROLE_ADMIN"})
    public String userInfo() {
        return userService.getLoggedInUser().getUsername() + userService.getLoggedInUser().getAuthorities();
    }

 }
