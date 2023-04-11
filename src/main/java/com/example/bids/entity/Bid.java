package com.example.bids.entity;

import com.example.bids.entity.listener.DateListener;
import com.example.bids.entity.listener.LibraryEntityListener;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@EntityListeners(value = { LibraryEntityListener.class })
public abstract class Bid implements DateListener {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "buyer_id")
    private User buyer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @Column(nullable = false)
    private int price;

    private LocalDateTime CreatedAt;
    private LocalDateTime EndedAt;
}