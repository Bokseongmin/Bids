package com.example.bids.dto;

import com.example.bids.entity.Category;
import com.example.bids.entity.Image;
import com.example.bids.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    private User confirmUser;

    private int confirmPrice;

    private int bidCount;

    private List<Image> itemImages = new ArrayList<>();

    private int status;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private LocalDateTime endedAt;
}
