package com.example.bids.entity;

import com.example.bids.entity.listener.DateListener;
import com.example.bids.entity.listener.LibraryEntityListener;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Builder
@EntityListeners(value = { LibraryEntityListener.class })
public class Item implements DateListener {
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

    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "item_images")
    private List<Image> itemImages = new ArrayList<>();

    private int startPrice;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "confirm_id")
    private User confirmUser;

    private int confirmPrice;

    @ColumnDefault("0")
    private int status;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private LocalDateTime endedAt;
}
