package com.example.bids.repository;

import com.example.bids.entity.Category;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CategoryRepositoryTest {
    @Autowired
    CategoryRepository categoryRepository;

//    String[] parents = {"디지털", "패션", "가구", "스포츠", "자동차", "도서"};
    @Test
    void addCategory() {
        var parentName = "도서";
        var subName = "외국어";

        Category parent = categoryRepository.findByName(parentName).orElse(null);
        if(parent == null) {
            parent = Category.builder().name(parentName).build();
            parent = categoryRepository.save(parent);
            System.out.println(parent);
        }

        var sub = Category.builder().name(subName).parent(parent).level(parent.getLevel() + 1).build();

        categoryRepository.save(sub);
    }
}