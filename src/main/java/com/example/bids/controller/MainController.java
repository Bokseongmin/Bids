package com.example.bids.controller;

import com.example.bids.entity.UserDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@Controller
public class MainController {

    @GetMapping("/")
    public String main(Model model, HttpSession session) {
        UserDto user = (UserDto) session.getAttribute("user");
        model.addAttribute("user", user);
        return "main";
    }
}
