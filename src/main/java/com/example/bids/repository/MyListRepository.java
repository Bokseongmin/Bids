package com.example.bids.repository;

import com.example.bids.entity.MyList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MyListRepository extends JpaRepository<MyList, Long> {
}
