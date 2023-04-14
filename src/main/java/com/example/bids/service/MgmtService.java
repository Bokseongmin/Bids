package com.example.bids.service;

import com.example.bids.dto.CategoryDto;
import com.example.bids.dto.ItemDto;
import com.example.bids.dto.MyListDto;
import com.example.bids.entity.*;
import com.example.bids.entity.UserDto;
import com.example.bids.repository.CategoryRepository;
import com.example.bids.repository.ItemRepository;
import com.example.bids.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.hibernate.proxy.HibernateProxy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MgmtService {
    private final ItemRepository itemRepository;

    private final UserRepository userRepository;

    private final CategoryRepository categoryRepository;

    public void add(Map<String, String> data) {
        Optional<User> user = userRepository.findByUserName(data.get("seller"));
        Optional<Category> category = categoryRepository.findByName("휴대폰");
        if(user.isPresent() && category.isPresent()) {
            ItemDto itemDto = ItemDto.builder()
                    .title(data.get("title"))
                    .seller(user.get())
                    .description(data.get("description"))
                    .category(category.get())
                    .startPrice(Integer.parseInt(data.get("startPrice")))
                    .imageUrl(data.get("imageUrl"))
                    .build();

            itemRepository.save(item_dtoToEntity(itemDto));
        }
    }

    @Transactional
    public List<ItemDto> get_myList(UserDto userDto) {
        Optional<User> user = userRepository.findByEmail(userDto.getEmail());
        List<ItemDto> itemDtos = new ArrayList<>();
        if(user.isPresent()) {
            List<Item> items = itemRepository.findItemsBySeller(user.get()).orElseGet(()-> null);
            for(Item item : items) {
                CategoryDto categoryDto = category_entityToDto(item.getCategory());
                categoryDto.setChildren(categoryDto.getChildren());
                ItemDto itemDto = item_entityToDto(item);
                itemDto.setCategory(category_dtoToEntity(categoryDto));
                itemDtos.add(itemDto);
            }
        }
        return itemDtos;
    }

    @Transactional
    public List<CategoryDto> categoryDtoList() {
        List<Category> category = categoryRepository.findAll();
        List<CategoryDto> categoryDtos = new ArrayList<>();

        for(Category getCategory : category) {
            CategoryDto categoryDto = new CategoryDto();
            categoryDto.setIdx(getCategory.getIdx());
            categoryDto.setName(getCategory.getName());
            categoryDto.setParent(getCategory.getParent());
            categoryDto.setChildren(getCategory.getChildren());
            categoryDto.setLevel(getCategory.getLevel());

            categoryDtos.add(categoryDto);
        }
        return categoryDtos;
    }

    private ItemDto item_entityToDto(Item item) {
        ItemDto dto = ItemDto.builder()
                .idx(item.getIdx())
                .title(item.getTitle())
                .seller(item.getSeller())
                .description(item.getDescription())
                .category(item.getCategory())
                .startPrice(item.getStartPrice())
                .imageUrl(item.getImageUrl())
                .build();
        return dto;
    }

    private Item item_dtoToEntity(ItemDto itemDto) {
        Item entity = Item.builder()
                .idx(itemDto.getIdx())
                .title(itemDto.getTitle())
                .seller(itemDto.getSeller())
                .description(itemDto.getDescription())
                .category(itemDto.getCategory())
                .startPrice(itemDto.getStartPrice())
                .imageUrl(itemDto.getImageUrl())
                .build();
        return entity;
    }

    private CategoryDto category_entityToDto(Category category) {
        CategoryDto categoryDto = CategoryDto.builder()
                .idx(category.getIdx())
                .name(category.getName())
                .parent(category.getParent())
                .children(category.getChildren())
                .level(category.getLevel())
                .build();

        return categoryDto;
    }

    private Category category_dtoToEntity(CategoryDto categoryDto) {
        Category category = Category.builder()
                .idx(categoryDto.getIdx())
                .name(categoryDto.getName())
                .parent(categoryDto.getParent())
                .children(categoryDto.getChildren())
                .level(categoryDto.getLevel())
                .build();

        return category;
    }
}
