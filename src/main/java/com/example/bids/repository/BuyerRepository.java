package com.example.bids.repository;

import com.example.bids.entity.Bid;
import com.example.bids.entity.Buyer;
import com.example.bids.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BuyerRepository extends JpaRepository<Buyer, Long> {
    @Query("SELECT MAX(b.price) FROM Buyer b WHERE b.bid.idx = :bidIdx")
    Optional<Integer> findMaxBuyerCountByIdx(@Param("bidIdx") Long bidIdx);

    @Query("SELECT b FROM Buyer b WHERE b.bid.idx = :bidIdx")
    Optional<List<Buyer>> findByBidIdx(@Param("bidIdx") Long bidIdx);

    Optional<Buyer> findByUserIdx(Long userIdx);
}
