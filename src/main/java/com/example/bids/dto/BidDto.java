package com.example.bids.dto;

import com.example.bids.entity.Category;
import com.example.bids.entity.Item;
import com.example.bids.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class BidDto {
    private Long idx;
    private List<User> buyer = new ArrayList<>();

    private Item item;
    private User confirmUser;

    private int confirmPrice;
    private LocalDateTime createdAt;
    private LocalDateTime endedAt;
}
