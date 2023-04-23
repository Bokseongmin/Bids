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

    @Test
    void addCategory() {
        Category parentCategory = Category.builder()
                .name("디지털")
                .level(0)
                .code("100")
                .codeRef(null)
                .build();
        categoryRepository.save(parentCategory);

        String[] childCategoryNames = {"컴퓨터", "TV", "모바일"};
        for (int i = 0; i < childCategoryNames.length; i++) {
            Category childCategory = Category.builder()
                    .name(childCategoryNames[i])
                    .level(1)
                    .code(String.format("10%d", i + 2))
                    .codeRef("100")
                    .build();
            categoryRepository.save(childCategory);
        }
        categoryRepository.save(parentCategory);

        Category parentCategory2 = Category.builder()
                .name("패션")
                .level(0)
                .code("200")
                .codeRef(null)
                .build();
        categoryRepository.save(parentCategory2);

        String[] childCategoryNames2 = {"모자", "상의", "하의", "신발", "악세사리"};
        for (int i = 0; i < childCategoryNames2.length; i++) {
            Category childCategory = Category.builder()
                    .name(childCategoryNames2[i])
                    .level(1)
                    .code(String.format("20%d", i + 1))
                    .codeRef("200")
                    .build();
            categoryRepository.save(childCategory);
        }
        categoryRepository.save(parentCategory2);

        Category parentCategory3 = Category.builder()
                .name("가구")
                .level(0)
                .code("300")
                .codeRef(null)
                .build();
        categoryRepository.save(parentCategory3);

        String[] childCategoryNames3 = {"침실", "거실", "주방", "수납"};
        for (int i = 0; i < childCategoryNames3.length; i++) {
            Category childCategory = Category.builder()
                    .name(childCategoryNames3[i])
                    .level(1)
                    .code(String.format("30%d", i + 1))
                    .codeRef("300")
                    .build();
            categoryRepository.save(childCategory);
        }
        categoryRepository.save(parentCategory3);

        Category parentCategory4 = Category.builder()
                .name("스포츠")
                .level(0)
                .code("400")
                .codeRef(null)
                .build();
        categoryRepository.save(parentCategory4);

        String[] childCategoryNames4 = {"축구", "야구", "농구"};
        for (int i = 0; i < childCategoryNames4.length; i++) {
            Category childCategory = Category.builder()
                    .name(childCategoryNames4[i])
                    .level(1)
                    .code(String.format("40%d", i + 1))
                    .codeRef("400")
                    .build();
            categoryRepository.save(childCategory);
        }
        categoryRepository.save(parentCategory4);

        Category parentCategory5 = Category.builder()
                .name("자동차")
                .level(0)
                .code("500")
                .codeRef(null)
                .build();
        categoryRepository.save(parentCategory5);

        String[] childCategoryNames5 = {"축구", "야구", "농구"};
        for (int i = 0; i < childCategoryNames5.length; i++) {
            Category childCategory = Category.builder()
                    .name(childCategoryNames5[i])
                    .level(1)
                    .code(String.format("50%d", i + 1))
                    .codeRef("500")
                    .build();
            categoryRepository.save(childCategory);
        }
        categoryRepository.save(parentCategory5);

        Category parentCategory6 = Category.builder()
                .name("도서")
                .level(0)
                .code("600")
                .codeRef(null)
                .build();
        categoryRepository.save(parentCategory6);

        String[] childCategoryNames6 = {"전공", "만화", "소설", "에세이"};
        for (int i = 0; i < childCategoryNames6.length; i++) {
            Category childCategory = Category.builder()
                    .name(childCategoryNames6[i])
                    .level(1)
                    .code(String.format("60%d", i + 1))
                    .codeRef("600")
                    .build();
            categoryRepository.save(childCategory);
        }
        categoryRepository.save(parentCategory6);
    }
}