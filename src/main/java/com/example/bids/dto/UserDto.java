package com.example.bids.entity;

import com.example.bids.entity.listener.DateListener;
import com.example.bids.entity.listener.LibraryEntityListener;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class UserDto implements DateListener {

    private Long idx;
    private String userName;
    private String email;
    private String userPw;
    private String phoneNo;
    private String addrCode;
    private String addr1;
    private String addr2;
    private String addr3;
    private String userImg;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
