package com.example.bids.controller;

import com.example.bids.dto.BidDto;
import com.example.bids.dto.BuyerDto;
import com.example.bids.dto.CategoryDto;
import com.example.bids.dto.ItemDto;
import com.example.bids.entity.UserDto;
import com.example.bids.service.BidService;
import com.example.bids.service.MgmtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
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

    @GetMapping("/dashboard/list")
    @ResponseBody
    public ResponseEntity get_homeList() {

        return ResponseEntity.ok("");
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
        System.out.println(data.toString());
        bidService.start(data, userDto);
    }


    @GetMapping("/bids")
    public String get_bids() {
        return "/pages/mgmt/bids";
    }

    @GetMapping("/bids/get")
    @ResponseBody
    public ResponseEntity get_bidsGet(HttpSession session) {
        UserDto userDto = (UserDto) session.getAttribute("user");
        List<BidDto> bidDtos = mgmtService.get_myBid(userDto);
        return ResponseEntity.ok(bidDtos);
    }

    @PostMapping("/bids")
    public String post_bids() {
        return "/";
    }

    @GetMapping("/add")
    public String get_add(Model model) {
        List<CategoryDto> categoryDtos = mgmtService.categoryDtos();
        model.addAttribute("category", categoryDtos);
        return "pages/mgmt/add";
    }

    @PostMapping("/add")
    @ResponseBody
    public ResponseEntity post_add(@RequestPart(value = "key") Map<String, String> data, @RequestPart(value = "file", required = false) List<MultipartFile> itemImages) throws IOException {
        mgmtService.add(data, itemImages);
        System.out.println(data);
        return ResponseEntity.status(HttpStatus.OK).body("");
    }

    @GetMapping("/add/category")
    @ResponseBody
    public ResponseEntity get_category(@RequestParam("parentIdx") String parentIdx) {
        List<CategoryDto> categoryDtos = mgmtService.get_categoryDtoList(Long.valueOf(parentIdx));
        System.out.println(categoryDtos.toString());
        return ResponseEntity.status(HttpStatus.OK).body(categoryDtos);
    }

    @GetMapping("/buyer")
    public String get_bidBuyer(@RequestParam("bid") Long idx, Model model) {
        List<BuyerDto> buyerDtos = mgmtService.get_Buyer(idx);
        System.out.println(buyerDtos.toString());
        model.addAttribute("buyer", buyerDtos);
        return "pages/mgmt/buyer";
    }

    @PostMapping("/buyer/confirm")
    @ResponseBody
    public ResponseEntity post_buyerConfirm(@RequestParam("bidNum") Long idx, @RequestParam("userName") String userName) {
        mgmtService.confirm_buy(idx, userName);
        return ResponseEntity.ok(idx);
    }

    @GetMapping("/t_history")
    public String get_t_history() {
        return "pages/mgmt/t_history";
    }

    @GetMapping("/t_history/get")
    @ResponseBody
    public ResponseEntity get_historyGet(HttpSession session) {
        UserDto userDto = (UserDto) session.getAttribute("user");
        List<ItemDto> itemDtos = mgmtService.history(userDto);
        return ResponseEntity.ok(itemDtos);
    }

    @GetMapping("/b_history")
    public String get_b_history() {
        return "pages/mgmt/b_history";
    }

    @GetMapping("/b_history/get")
    @ResponseBody
    public ResponseEntity get_b_historyGet(HttpSession session) {
        UserDto userDto = (UserDto) session.getAttribute("user");
        List<ItemDto> itemDtos = mgmtService.b_history(userDto);
        return ResponseEntity.ok(itemDtos);
    }
}
