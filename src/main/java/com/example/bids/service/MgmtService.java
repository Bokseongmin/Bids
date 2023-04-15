package com.example.bids.service;

import com.example.bids.dto.CategoryDto;
import com.example.bids.dto.ItemDto;
import com.example.bids.dto.MyListDto;
import com.example.bids.entity.*;
import com.example.bids.entity.UserDto;
import com.example.bids.repository.CategoryRepository;
import com.example.bids.repository.ItemRepository;
import com.example.bids.repository.UserRepository;
import com.example.bids.utill.Converter;
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

    private final Converter converter;

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

            itemRepository.save(converter.item_dtoToEntity(itemDto));
        }
    }

    @Transactional
    public List<ItemDto> get_myList(UserDto userDto) {
        Optional<User> user = userRepository.findByEmail(userDto.getEmail());
        List<ItemDto> itemDtos = new ArrayList<>();
        if(user.isPresent()) {
            List<Item> items = itemRepository.findItemsBySeller(user.get()).orElseGet(()-> null);
            for(Item item : items) {
                CategoryDto categoryDto = converter.category_entityToDto(item.getCategory());
                categoryDto.setChildren(categoryDto.getChildren());
                ItemDto itemDto = converter.item_entityToDto(item);
                itemDto.setCategory(converter.category_dtoToEntity(categoryDto));
                itemDtos.add(itemDto);
            }
        }
        return itemDtos;
    }

    @Transactional
    public List<CategoryDto> categoryDtoList() {
        List<Category> category = categoryRepository.findAll();
        List<CategoryDto> categoryDtos = new ArrayList<>();

        for (Category getCategory : category) {
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
}
