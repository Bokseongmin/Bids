package com.example.bids.controller;

import com.example.bids.dto.CategoryDto;
import com.example.bids.dto.ItemDto;
import com.example.bids.entity.Category;
import com.example.bids.entity.User;
import com.example.bids.entity.UserDto;
import com.example.bids.service.BidService;
import com.example.bids.service.MgmtService;
import com.example.bids.service.SignService;
import com.example.bids.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.function.EntityResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RequestMapping("/mgmt")
@Controller
public class MgmtController {

    private final MgmtService mgmtService;
    private final BidService bidService;

    @GetMapping("/profile")
    public String get_profile() {
        return "/pages/mgmt/profile";
    }

    @GetMapping("/dashboard")
    public String get_home() {
        return "/pages/mgmt/dashboard";
    }

    @GetMapping("/list")
    public String get_list() {
        return "/pages/mgmt/list";
    }

    @GetMapping("/list/get")
    @ResponseBody
    public ResponseEntity get_listGet(HttpSession session) {
        UserDto userDto = (UserDto) session.getAttribute("user");
        List<ItemDto> itemDtos = mgmtService.get_myList(userDto);
        return ResponseEntity.ok(itemDtos);
    }

    @PostMapping("/list/start")
    @ResponseBody
    public void post_start(@RequestParam Map<String, String> data, HttpSession session) {
        UserDto userDto = (UserDto) session.getAttribute("user");
        bidService.start(data, userDto);
    }


    @GetMapping("/bids")
    public String get_bids() {
        return "/pages/mgmt/bids";
    }

    @PostMapping("/bids")
    public String post_bids() {
        return "/";
    }
    @GetMapping("/add")
    public String get_add(Model model) {
        List<CategoryDto> categoryDtos = mgmtService.categoryDtoList();
        model.addAttribute("category",categoryDtos);
        return "pages/mgmt/add";
    }

    @PostMapping("/add")
    @ResponseBody
    public ResponseEntity post_add(@RequestParam Map<String, String> data) {
        mgmtService.add(data);
        return ResponseEntity.status(HttpStatus.OK).body("");
    }
}
