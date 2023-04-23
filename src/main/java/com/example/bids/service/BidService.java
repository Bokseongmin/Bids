package com.example.bids.service;

import com.example.bids.dto.*;
import com.example.bids.entity.*;
import com.example.bids.entity.UserDto;
import com.example.bids.repository.*;
import com.example.bids.utill.Converter;
import lombok.RequiredArgsConstructor;
import net.bytebuddy.asm.Advice;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.catalog.Catalog;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BidService {

    private final BidRepository bidRepository;

    private final ItemRepository itemRepository;

    private final UserRepository userRepository;

    private final BuyerRepository buyerRepository;

    private final ImageRepository imageRepository;

    private final Converter converter;

    public void start(Map<String, String> map, UserDto userDto) {
        Optional<Item> item = itemRepository.findById(Long.valueOf(map.get("num")));
        System.out.println(">>>>>>>>>>>" + map);
        LocalDateTime now = LocalDateTime.now();
        int year = now.getYear();
        Month month = now.getMonth();
        int day = now.getDayOfMonth();
        int hour = now.getHour() + Integer.parseInt(map.get("hour"));
        int min = now.getMinute() + Integer.parseInt(map.get("min"));
        int sec = now.getSecond();
        if (min > 59) {
            hour++;
            min = min - 60;
        }
        if (hour > 23) {
            day++;
            hour = hour - 24;
        }
        if (day > now.getMonth().maxLength()) {
            month.plus(1);
            month = Month.of(month.getValue() - 12);
        }
        if (month.getValue() > 12) {
            year++;
        }
        LocalDateTime endedAt = LocalDateTime.of(year, month, day, hour, min, sec);
        System.out.println("endedAt" + endedAt);
        System.out.println(endedAt);
        if (item.isPresent()) {
            ItemDto itemDto = converter.item_entityToDto(item.get());
            itemDto.setStatus(1);
            BidDto bidDto = BidDto.builder()
                    .item(converter.item_dtoToEntity(itemDto))
                    .createdAt(LocalDateTime.now())
                    .endedAt(endedAt)
                    .build();
            bidRepository.save(converter.bid_dtoToEntity(bidDto));
        }
    }

    @Transactional
    public List<BidDto> main() {
        List<Bid> bids = bidRepository.findTop4ByOrderByBuyerCountDesc();
        List<BidDto> bidDtos = new ArrayList<>();
        LocalDateTime now = LocalDateTime.now();
        for (int i = 0; i < bids.size(); i++) {
            Optional<Item> item = itemRepository.findById(bids.get(i).getItem().getIdx());
            if (item.isPresent()) {
                CategoryDto categoryDto = converter.category_entityToDto(item.get().getCategory());
                ItemDto itemDto = converter.item_entityToDto(item.get());
                UserDto seller = converter.user_entityToDto(item.get().getSeller());
                itemDto.setSeller(converter.user_dtoToEntity(seller));
                itemDto.setCategory(converter.category_dtoToEntity(categoryDto));

                BidDto bidDto = BidDto.builder()
                        .idx(bids.get(i).getIdx())
                        .item(converter.item_dtoToEntity(itemDto))
                        .endedAt(bids.get(i).getEndedAt())
                        .build();

                if (now.compareTo(bidDto.getEndedAt()) > 0) {
                    bidRepository.delete(converter.bid_dtoToEntity(bidDto));
                } else {
                    bidDtos.add(bidDto);
                }
            }
        }
        System.out.println(bidDtos.toString());
        return bidDtos;
    }

    @Transactional
    public BidDto info(Long idx) {
        Optional<Bid> bid = bidRepository.findById(idx);
        BidDto bidDto = null;
        if (bid.isPresent()) {
            Optional<Item> item = itemRepository.findById(bid.get().getItem().getIdx());
            if (item.isPresent()) {
                User seller = item.get().getSeller();
                Category category = item.get().getCategory();
                ItemDto itemDto = converter.item_entityToDto(item.get());
                itemDto.setSeller(seller);
                itemDto.setCategory(category);

                bidDto = converter.bid_entityToDto(bid.get());
                bidDto.setItem(converter.item_dtoToEntity(itemDto));
            }
        }
        System.out.println(bidDto.toString());
        return bidDto;
    }

    @Transactional
    public ItemDto infoItem(Long idx) {
        Optional<Item> item = itemRepository.findById(idx);
        ItemDto itemDto = null;
        if(item.isPresent()) {
            itemDto = converter.item_entityToDto(item.get());
        }
        System.out.println(itemDto.toString());
        return itemDto;
    }

    @Transactional
    public void sendBuyer(Map<String, String> data, UserDto userDto) {
        Optional<Bid> bid = bidRepository.findById(Long.valueOf(data.get("idx")));
        Optional<User> user = userRepository.findById(userDto.getIdx());
        Optional<Item> item = itemRepository.findById(bid.get().getItem().getIdx());
        if (bid.isPresent() && item.isPresent()) {
            User seller = item.get().getSeller();
            if (user.isPresent()) {
                ItemDto itemDto = converter.item_entityToDto(item.get());
                itemDto.setSeller(seller);
                BidDto bidDto = converter.bid_entityToDto(bid.get());
                bidDto.setItem(converter.item_dtoToEntity(itemDto));
                BuyerDto buyerDto = BuyerDto.builder()
                        .user(converter.user_dtoToEntity(userDto))
                        .bid(converter.bid_dtoToEntity(bidDto))
                        .price(Integer.parseInt(data.get("sendPrice")))
                        .createdAt(LocalDateTime.now())
                        .build();
                System.out.println(buyerDto.toString());
                buyerRepository.save(converter.buyer_dtoToEntity(buyerDto));
            } else {

            }
        }
    }

    @Transactional
    public List<BidDto> bids() {
        List<Bid> bids = bidRepository.findAll();
        List<BidDto> bidDtos = new ArrayList<>();
        if (bids != null) {
            for (Bid bid : bids) {
                BidDto bidDto = converter.bid_entityToDto(bid);

                Item item = (Item) Hibernate.unproxy(bid.getItem());
                User seller = (User) Hibernate.unproxy(item.getSeller());
                Category category = (Category) Hibernate.unproxy(item.getCategory());

                ItemDto itemDto = converter.item_entityToDto(bidDto.getItem());
                itemDto.setSeller(seller);
                itemDto.setCategory(category);
                itemDto.setItemImages(item.getItemImages());
                bidDto.setItem(converter.item_dtoToEntity(itemDto));

                bidDtos.add(bidDto);
            }
        }
        System.out.println(bidDtos.toString());
        return bidDtos;
    }

    @Transactional
    public List<ItemDto> list() {
        List<Item> items = itemRepository.findAll();
        List<ItemDto> itemDtos = new ArrayList<>();
        for (Item item : items) {
            if (item.getConfirmUser() != null) {
                UserDto seller = converter.user_entityToDto(item.getSeller());
                CategoryDto categoryDto = converter.category_entityToDto(item.getCategory());
                UserDto confirmUser = converter.user_entityToDto(item.getConfirmUser());

                item.setSeller(converter.user_dtoToEntity(seller));
                item.setCategory(converter.category_dtoToEntity(categoryDto));
                item.setConfirmUser(converter.user_dtoToEntity(confirmUser));

                List<Image> images = item.getItemImages();
                item.setItemImages(images);

                itemDtos.add(converter.item_entityToDto(item));
            }
        }
        System.out.println(itemDtos.toString());
        return itemDtos;
    }
}
