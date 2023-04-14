package com.example.bids.dto;

import com.example.bids.entity.Category;
import com.example.bids.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ItemDto {
    private Long idx;

    private User seller;

    private String title;

    private String description;

    private Category category;

    private int startPrice;

    private int currentPrice;

    private int bidCount;

    private String imageUrl;

    private String status;

    private LocalDateTime createdAt;
}
