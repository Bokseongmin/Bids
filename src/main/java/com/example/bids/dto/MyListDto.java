package com.example.bids.dto;

import com.example.bids.entity.Category;
import com.example.bids.entity.Item;
import com.example.bids.entity.User;
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
public class MyListDto {

    private Long idx;
    private Item item;
}
