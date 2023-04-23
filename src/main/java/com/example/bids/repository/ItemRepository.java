package com.example.bids.repository;

import com.example.bids.entity.Item;
import com.example.bids.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    Optional<List<Item>> findItemsBySeller(User user);

    Optional<List<Item>> findItemsByConfirmUser(User user);
}
