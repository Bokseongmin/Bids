package com.example.bids.repository;

import com.example.bids.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByName(String name);

    @Query("SELECT c FROM Category c WHERE c.codeRef = (SELECT code FROM Category WHERE idx = :idx)")
    Optional<List<Category>> findByCodeRef(@Param("idx") Long idx);
}
