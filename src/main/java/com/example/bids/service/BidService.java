package com.example.bids.service;

import com.example.bids.dto.BidDto;
import com.example.bids.dto.BuyerDto;
import com.example.bids.dto.CategoryDto;
import com.example.bids.dto.ItemDto;
import com.example.bids.entity.Bid;
import com.example.bids.entity.Item;
import com.example.bids.entity.User;
import com.example.bids.entity.UserDto;
import com.example.bids.repository.BidRepository;
import com.example.bids.repository.ItemRepository;
import com.example.bids.repository.UserRepository;
import com.example.bids.utill.Converter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    private final Converter converter;

    public void start(Map<String, String> map, UserDto userDto) {
        Optional<Item> item = itemRepository.findById(Long.valueOf(map.get("num")));
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
        LocalDateTime enaded = LocalDateTime.of(year, month, day, hour, min, sec);
        if (item.isPresent()) {
            ItemDto itemDto = converter.item_entityToDto(item.get());
            BidDto bidDto = BidDto.builder()
                    .item(converter.item_dtoToEntity(itemDto))
                    .createdAt(LocalDateTime.now())
                    .endedAt(enaded)
                    .build();
            bidRepository.save(converter.bid_dtoToEntity(bidDto));
        }
    }

    @Transactional(readOnly = true)
    public List<BidDto> main() {
        List<Bid> bids = bidRepository.findTop4ByOrderByBuyerCountDesc();
        List<BidDto> bidDtos = new ArrayList<>();
        for (int i = 0; i < bids.size(); i++) {
            Optional<Item> item = itemRepository.findById(bids.get(i).getItem().getIdx());
            if(item.isPresent()) {
                User seller = item.get().getSeller();
                BidDto bidDto = BidDto.builder()
                        .idx(bids.get(i).getIdx())
                        .item(Item.builder()
                                .idx(item.get().getIdx())
                                .seller(User.builder()
                                        .idx(seller.getIdx())
                                        .userName(seller.getUserName())
                                        .phoneNo(seller.getPhoneNo())
                                        .userImg(seller.getUserImg())
                                        .createdAt(seller.getCreatedAt())
                                        .build())
                                .title(item.get().getTitle())
                                .description(item.get().getDescription())
                                .startPrice(item.get().getStartPrice())
                                .currentPrice(item.get().getCurrentPrice())
                                .bidCount(item.get().getBidCount())
                                .imageUrl(item.get().getImageUrl())
                                .status(item.get().getStatus())
                                .createdAt(item.get().getCreatedAt())
                                .build())
                        .endedAt(bids.get(i).getEndedAt())
                        .build();
                bidDtos.add(bidDto);
            }
        }
        return bidDtos;
    }

    @Transactional
    public BidDto info(Long idx) {
        Optional<Bid> bid = bidRepository.findById(idx);
        BidDto bidDto = null;
        if (bid.isPresent()) {
            Optional<Item> item = itemRepository.findById(bid.get().getItem().getIdx());
            if(item.isPresent()) {
                User seller = item.get().getSeller();
                bidDto = BidDto.builder()
                        .idx(bid.get().getIdx())
                        .item(Item.builder()
                                .idx(item.get().getIdx())
                                .seller(User.builder()
                                        .idx(seller.getIdx())
                                        .userName(seller.getUserName())
                                        .userImg(seller.getUserImg())
                                        .createdAt(seller.getCreatedAt())
                                        .build())
                                .title(item.get().getTitle())
                                .description(item.get().getDescription())
                                .startPrice(item.get().getStartPrice())
                                .currentPrice(item.get().getCurrentPrice())
                                .bidCount(item.get().getBidCount())
                                .imageUrl(item.get().getImageUrl())
                                .status(item.get().getStatus())
                                .createdAt(item.get().getCreatedAt())
                                .build())
                        .endedAt(bid.get().getEndedAt())
                        .build();
            }
        }
        return bidDto;
    }

    @Transactional
    public void sendBuyer(Map<String, String> data) {
        Optional<Bid> bid = bidRepository.findById(Long.valueOf(data.get("idx")));
        if(bid.isPresent()) {

        }
    }
}
