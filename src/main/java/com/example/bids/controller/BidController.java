package com.example.bids.controller;

import com.example.bids.dto.BidDto;
import com.example.bids.dto.CategoryDto;
import com.example.bids.dto.ItemDto;
import com.example.bids.entity.UserDto;
import com.example.bids.service.BidService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RequestMapping("/bid")
@Controller
public class BidController {

    private final BidService bidService;

    @GetMapping("/main")
    public ResponseEntity get_main() {
        List<BidDto> bidDtos = bidService.main();
        return ResponseEntity.ok().body(bidDtos);
    }

    @GetMapping("/list")
    public ResponseEntity get_list() {
        List<ItemDto> itemDtos = bidService.list();
        return ResponseEntity.ok().body(itemDtos);
    }

    @GetMapping("/bids")
    public ResponseEntity get_bids() {
        List<BidDto> bidDtos = bidService.bids();
        return ResponseEntity.ok().body(bidDtos);
    }

    @GetMapping("/info")
    public String get_info(@RequestParam("idx") String idx, Model model) {
        BidDto bidDto = bidService.info(Long.valueOf(idx));
        model.addAttribute("bid", bidDto);
        return "pages/info";
    }

    @GetMapping("/infoItem")
    public String get_infoItem(@RequestParam("idx") String idx, Model model) {
        ItemDto itemDto = bidService.infoItem(Long.valueOf(idx));
        model.addAttribute("item", itemDto);
        return "pages/infoItem";
    }

    @PostMapping("/info")
    public String post_info(@RequestParam Map<String, String> data, HttpSession session) {
        UserDto user = (UserDto) session.getAttribute("user");
        bidService.sendBuyer(data, user);
        return "redirect:/bid/info?idx=" + data.get("idx");
    }
}
