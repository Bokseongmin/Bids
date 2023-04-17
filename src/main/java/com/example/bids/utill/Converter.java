package com.example.bids.utill;

import com.example.bids.dto.BidDto;
import com.example.bids.dto.CategoryDto;
import com.example.bids.dto.ItemDto;
import com.example.bids.entity.*;
import com.example.bids.entity.UserDto;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Converter {
    public User user_dtoToEntity(UserDto userDto) {
        User user = User.builder()
                .idx(userDto.getIdx())
                .userName(userDto.getUserName())
                .email(userDto.getEmail())
                .userPw(userDto.getUserPw())
                .phoneNo(userDto.getPhoneNo())
                .addrCode(userDto.getAddrCode()).addr1(userDto.getAddr1()).addr2(userDto.getAddr2()).addr3(userDto.getAddr3())
                .build();
        return user;
    }

    public UserDto user_entityToDto(User user) {
        UserDto userDto = UserDto.builder()
                .idx(user.getIdx())
                .userName(user.getUserName())
                .email(user.getEmail())
                .userPw(user.getUserPw())
                .phoneNo(user.getPhoneNo())
                .addrCode(user.getAddrCode()).addr1(user.getAddr1()).addr2(user.getAddr2()).addr3(user.getAddr3())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .idx(user.getIdx())
                .build();
        return userDto;
    }

    public ItemDto item_entityToDto(Item item) {
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

    public Item item_dtoToEntity(ItemDto itemDto) {
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

    public CategoryDto category_entityToDto(Category category) {
        CategoryDto categoryDto = CategoryDto.builder()
                .idx(category.getIdx())
                .name(category.getName())
                .parent(category.getParent())
                .children(category.getChildren())
                .level(category.getLevel())
                .build();

        return categoryDto;
    }

    public Category category_dtoToEntity(CategoryDto categoryDto) {
        Category category = Category.builder()
                .idx(categoryDto.getIdx())
                .name(categoryDto.getName())
                .parent(categoryDto.getParent())
                .children(categoryDto.getChildren())
                .level(categoryDto.getLevel())
                .build();

        return category;
    }

    public Bid bid_dtoToEntity(BidDto bidDto) {
        Bid bid = Bid.builder()
                .idx(bidDto.getIdx())
                .buyerCount(bidDto.getBuyerCount())
                .item(bidDto.getItem())
                .confirmUser(bidDto.getConfirmUser())
                .createdAt(bidDto.getCreatedAt())
                .endedAt(bidDto.getEndedAt())
                .build();
        return bid;
    }

    public BidDto bid_entityToDto(Bid bid) {
        BidDto bidDto = BidDto.builder()
                .idx(bid.getIdx())
                .buyerCount(bid.getBuyerCount())
                .item(bid.getItem())
                .confirmUser(bid.getConfirmUser())
                .createdAt(bid.getCreatedAt())
                .endedAt(bid.getEndedAt())
                .build();
        return bidDto;
    }
}
