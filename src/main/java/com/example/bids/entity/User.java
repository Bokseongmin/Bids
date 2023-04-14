package com.example.bids.entity;

import com.example.bids.entity.listener.DateListener;
import com.example.bids.entity.listener.LibraryEntityListener;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
@EntityListeners(value = { LibraryEntityListener.class })
public class User implements DateListener {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Column(nullable = false)
    private String userName;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String userPw;
    @Column(nullable = false)
    private String phoneNo;
    @Column(nullable = false)
    private String addrCode;
    @Column(nullable = false)
    private String addr1;
    private String addr2;
    private String addr3;
    private String userImg;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
