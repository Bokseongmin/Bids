package com.example.bids.dto;

import com.example.bids.entity.Bid;
import com.example.bids.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class BuyerDto {
    private Long idx;
    private User user;
    private Bid bid;
    private int price;

    private LocalDateTime createdAt;
}
