package com.example.bids.repository;

import com.example.bids.entity.Item;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ItemRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    ItemRepository itemRepository;

    @Rollback(value = false)
    @Transactional
    @Test
    void addItemTest() {
        var findUser = userRepository.findById(6L).orElseThrow();
        var findCategory = categoryRepository.findByName("휴대폰").orElseThrow();
        var myItem = Item.builder().title("아이폰15")
                .category(findCategory)
                .seller(findUser)
                .description("아이폰 15팔아요")
                .imageUrl("")
                .bidCount(10)
                .startPrice(100000)
                .currentPrice(10)
                .status("입찰 진행중")
                .build();
        itemRepository.save(myItem);
    }
}