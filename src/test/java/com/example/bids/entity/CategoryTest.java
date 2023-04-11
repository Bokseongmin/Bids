package com.example.bids.entity;

import com.example.bids.repository.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CategoryTest {

    @Autowired
    CategoryRepository categoryRepository;

    @Test
    void addCategory() {
        String[] parents = {"디지털", "패션", "가구", "스포츠", "자동차", "도서"};

        for(String parent : parents) {
            Optional<Category> getParent = categoryRepository.findByName(parent);
            if(!getParent.isPresent()) {
                var setParent = Category.builder().name(parent).build();
                categoryRepository.save(setParent);
                System.out.println("카테고리 저장 완료 : " + parent);
            }
        }
    }
}