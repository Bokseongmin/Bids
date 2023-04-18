package com.example.bids.repository;

import com.example.bids.entity.Bid;
import com.example.bids.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BidRepository extends JpaRepository<Bid, Long> {
    List<Bid> findTop4ByOrderByBuyerCountDesc();

    Optional<Bid> findByItemIdx(Long idx);

    Optional<List<Bid>> findByItemSellerIdx(Long sellerIdx);
}
