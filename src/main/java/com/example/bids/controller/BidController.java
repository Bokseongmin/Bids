package com.example.bids.controller;

import com.example.bids.dto.BidDto;
import com.example.bids.service.BidService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
        System.out.println(bidDtos.toString());
        return ResponseEntity.ok().body(bidDtos);
    }

    @GetMapping("/info")
    public String get_info(@RequestParam("idx") String idx, Model model) {
        BidDto bidDto = bidService.info(Long.valueOf(idx));
        model.addAttribute("bid", bidDto);
        return "pages/info";
    }
    @PostMapping("/info")
    public String post_info(@RequestParam Map<String, String> data) {
        bidService.sendBuyer(data);
        return "redirect:/bid/info?idx=" + data.get("idx");
    }
}
