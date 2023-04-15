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
        Optional<Item> item =  itemRepository.findById(Long.valueOf(map.get("num")));
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
        if(item.isPresent()) {
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
        List<Bid> bids = bidRepository.findTop4ByOrderByBuyerDesc();
        List<BidDto> bidDtos = new ArrayList<>();
        for(int i = 0; i < bids.size(); i++) {
            Item item = itemRepository.findById(bids.get(i).getIdx()).get();
            User seller = item.getSeller();

            // DTO 객체 생성 및 값 설정
            BidDto bidDto = BidDto.builder()
                    .idx(bids.get(i).getIdx())
                    .item(Item.builder()
                            .idx(item.getIdx())
                            .seller(User.builder()
                                    .idx(seller.getIdx())
                                    .userName(seller.getUserName())
                                    .phoneNo(seller.getPhoneNo())
                                    .userImg(seller.getUserImg())
                                    .createdAt(seller.getCreatedAt())
                                    .build())
                            .title(item.getTitle())
                            .description(item.getDescription())
                            .startPrice(item.getStartPrice())
                            .currentPrice(item.getCurrentPrice())
                            .bidCount(item.getBidCount())
                            .imageUrl(item.getImageUrl())
                            .status(item.getStatus())
                            .createdAt(item.getCreatedAt())
                            .build())
                    .endedAt(bids.get(i).getEndedAt())
                    .build();
            bidDtos.add(bidDto);
        }
        return bidDtos;
    }
}
