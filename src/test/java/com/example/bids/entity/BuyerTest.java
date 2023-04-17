package com.example.bids.entity;

import com.example.bids.dto.BuyerDto;
import com.example.bids.repository.BidRepository;
import com.example.bids.repository.BuyerRepository;
import com.example.bids.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BuyerTest {
    @Autowired
    private BuyerRepository buyerRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BidRepository bidRepository;

    @Test
    void add() {
        Optional<User> user = userRepository.findById(7L);
        if (user.isPresent()) {
            Optional<Bid> bid = bidRepository.findById(14L);
            if (bid.isPresent()) {
                Buyer buyer = Buyer.builder()
                        .user(user.get())
                        .bid(bid.get())
                        .price(100000)
                        .build();

                buyerRepository.save(buyer);

                Bid updatedBid = bid.get();
                updatedBid.setBuyerCount(updatedBid.getBuyerCount() + 1);

                bidRepository.save(updatedBid);
            }
        }
    }
}