package com.example.bids.controller;

import com.example.bids.dto.BidDto;
import com.example.bids.service.BidService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

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
}
