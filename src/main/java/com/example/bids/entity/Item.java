package com.example.bids.entity;

import com.example.bids.entity.listener.DateListener;
import com.example.bids.entity.listener.LibraryEntityListener;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Builder
@EntityListeners(value = { LibraryEntityListener.class })
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id")
    private User seller;

    @Column(nullable = false)
    private String title;

    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    private String imageUrl;

    private int startPrice;

    private int currentPrice;

    private int bidCount;

    private LocalDateTime createdAt;

    @Column(nullable = false, columnDefinition = "VARCHAR(50) DEFAULT '판매 중'")
    private String status;
}
