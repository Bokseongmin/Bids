package com.example.bids.dto;

import com.example.bids.entity.Bid;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class BuyerDto {
    private Long id;
    private String name;
    private List<Long> bidIds;
}
