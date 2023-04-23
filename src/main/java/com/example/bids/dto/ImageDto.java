package com.example.bids.dto;

import com.example.bids.entity.Item;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ImageDto {

    private Long idx;
    private String originalFileName;
    private String storedFileName;
    private LocalDateTime createdAt;

    private Item item;
}
