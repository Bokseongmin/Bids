package com.example.bids.controller;

import com.example.bids.entity.UserDto;
import com.example.bids.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

@RequiredArgsConstructor
@RequestMapping("/my")
@Controller
public class UserController {

    @GetMapping("/profile")
    public String get_profile() {
        return "/pages/my/profile";
    }

    @GetMapping("/manage")
    public String get_manage() {
        return "/pages/my/manage";
    }
}
