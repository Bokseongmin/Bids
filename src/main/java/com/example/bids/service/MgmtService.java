package com.example.bids.service;

import com.example.bids.dto.*;
import com.example.bids.entity.*;
import com.example.bids.entity.UserDto;
import com.example.bids.repository.*;
import com.example.bids.utill.Converter;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MgmtService {
    private final ItemRepository itemRepository;

    private final UserRepository userRepository;

    private final CategoryRepository categoryRepository;

    private final BidRepository bidRepository;

    private final BuyerRepository buyerRepository;

    private final ImageRepository imageRepository;

    private final Converter converter;

    private final ResourceLoader resourceLoader;

    public void add(Map<String, String> data, List<MultipartFile> itemImages) throws IOException {
        Optional<User> user = userRepository.findByUserName(data.get("seller"));
        Optional<Category> category = categoryRepository.findById(Long.valueOf(data.get("childrenIdx")));
        if (user.isPresent() && category.isPresent()) {
            if (itemImages.isEmpty()) {
                ItemDto itemDto = ItemDto.builder()
                        .title(data.get("title"))
                        .seller(user.get())
                        .description(data.get("description"))
                        .category(category.get())
                        .startPrice(Integer.parseInt(data.get("startPrice")))
                        .itemImages(null)
                        .build();
                itemRepository.save(converter.item_dtoToEntity(itemDto));
            } else {
                List<Image> images = new ArrayList<>();
                for (MultipartFile itemImage : itemImages) {
                    String originalFileName = itemImage.getOriginalFilename();
                    String storedFileName = System.currentTimeMillis() + "_" + originalFileName;

                    Resource resource = resourceLoader.getResource("classpath:static");
                    String absolutePath = resource.getFile().getAbsolutePath();
                    String savePath = absolutePath + "\\bid\\" + storedFileName;

                    itemImage.transferTo(new File(savePath));

                    Image image = Image.builder()
                            .originalFileName(originalFileName)
                            .storedFileName(storedFileName)
                            .createdAt(LocalDateTime.now())
                            .build();
                    imageRepository.save(image);
                    images.add(image);
                }

                Item item = Item.builder()
                        .title(data.get("title"))
                        .seller(user.get())
                        .description(data.get("description"))
                        .category(category.get())
                        .startPrice(Integer.parseInt(data.get("startPrice")))
                        .itemImages(images)
                        .build();
                itemRepository.save(item);
            }
        }
    }

    @Transactional
    public List<CategoryDto> categoryDtos() {
        List<Category> category = categoryRepository.findAll();
        List<CategoryDto> categoryDtos = new ArrayList<>();

        for (Category getCategory : category) {
            CategoryDto categoryDto = new CategoryDto();
            categoryDto.setIdx(getCategory.getIdx());
            categoryDto.setName(getCategory.getName());
            categoryDto.setCode(getCategory.getCode());
            categoryDto.setCodeRef(getCategory.getCodeRef());
            categoryDto.setLevel(getCategory.getLevel());

            categoryDtos.add(categoryDto);
        }
        return categoryDtos;
    }

    @Transactional
    public List<CategoryDto> get_categoryDtoList(Long parentIdx) {
        Optional<List<Category>> categories = categoryRepository.findByCodeRef(parentIdx);
        List<CategoryDto> categoryDtos = new ArrayList<>();
        if (categories.isPresent()) {
            for (Category getCategory : categories.get()) {
                CategoryDto categoryDto = converter.category_entityToDto(getCategory);
                categoryDtos.add(categoryDto);
            }
        }
        return categoryDtos;
    }

    @Transactional
    public List<ItemDto> get_myList(UserDto userDto) {
        Optional<User> user = userRepository.findById(userDto.getIdx());
        List<ItemDto> itemDtos = new ArrayList<>();
        if (user.isPresent()) {
            List<Item> items = itemRepository.findItemsBySeller(user.get()).orElseGet(() -> null);
            for (Item item : items) {
                Optional<Bid> bid = bidRepository.findByItemIdx(item.getIdx());
                if (bid.isEmpty()) {
                    CategoryDto categoryDto = converter.category_entityToDto(item.getCategory());
                    categoryDto.setCode(categoryDto.getCode());
                    categoryDto.setCodeRef(categoryDto.getCodeRef());
                    ItemDto itemDto = converter.item_entityToDto(item);
                    itemDto.setConfirmUser(new User());
                    itemDto.setCategory(converter.category_dtoToEntity(categoryDto));
                    itemDtos.add(itemDto);
                }
            }
        }
        System.out.println(itemDtos.toString());
        return itemDtos;
    }

    @Transactional
    public List<BidDto> get_myBid(UserDto userDto) {
        Optional<User> user = userRepository.findById(userDto.getIdx());
        List<BidDto> bidDtos = new ArrayList<>();
        if (user.isPresent()) {
            Optional<List<Bid>> bids = bidRepository.findByItemSellerIdx(user.get().getIdx());
            if (bids.isPresent()) {
                for (Bid bid : bids.get()) {
                    Optional<Integer> maxPrice = buyerRepository.findMaxBuyerCountByIdx(bid.getIdx());
                    Item item = (Item) Hibernate.unproxy(bid.getItem());
                    Hibernate.initialize(item.getItemImages());
                    if (item.getItemImages() == null || item.getItemImages().isEmpty()) {

                    }
                    CategoryDto categoryDto = converter.category_entityToDto(item.getCategory());
                    categoryDto.setCode(categoryDto.getCode());
                    categoryDto.setCodeRef(categoryDto.getCodeRef());
                    item.setCategory(converter.category_dtoToEntity(categoryDto));
                    BidDto bidDto = converter.bid_entityToDto(bid);
                    bidDto.setPrice(maxPrice.orElse(bid.getItem().getStartPrice()));
                    bidDto.setItem(item);
                    bidDtos.add(bidDto);
                }
            }
        }
        return bidDtos;
    }

    @Transactional
    public List<BuyerDto> get_Buyer(Long data) {
        List<BuyerDto> buyerDtos = new ArrayList<>();
        Optional<List<Buyer>> buyers = buyerRepository.findByBidIdx(data);
        if (buyers.isPresent()) {
            for (Buyer buyer : buyers.get()) {
                BuyerDto buyerDto = BuyerDto.builder()
                        .idx(buyer.getIdx())
                        .user(buyer.getUser())
                        .bid(buyer.getBid())
                        .price(buyer.getPrice())
                        .createdAt(buyer.getCreatedAt())
                        .build();
                buyerDtos.add(buyerDto);
            }
        }
        System.out.println(buyerDtos);
        return buyerDtos;
    }

    @Transactional
    public void confirm_buy(Long idx, String userName) {
        Optional<Bid> bid = Optional.of(bidRepository.getById(idx));
        Optional<User> user = userRepository.findByUserName(userName);
        Optional<Buyer> buyer = buyerRepository.findByUserIdxWithBidIdx(user.get().getIdx(), bid.get().getIdx());
        ItemDto itemDto = null;
        if (bid.isPresent() && user.isPresent()) {
            itemDto = converter.item_entityToDto(bid.get().getItem());
            itemDto.setConfirmUser(buyer.get().getUser());
            itemDto.setConfirmPrice(buyer.get().getPrice());
            itemDto.setEndedAt(LocalDateTime.now());
            itemDto.setStatus(2);
            itemRepository.save(converter.item_dtoToEntity(itemDto));
            buyerRepository.delete(buyer.get());
            bidRepository.delete(bid.get());
        }
    }

    @Transactional
    public List<ItemDto> history(UserDto userDto) {
        List<ItemDto> itemDtos = new ArrayList<>();
        Optional<List<Item>> items = itemRepository.findItemsBySeller(converter.user_dtoToEntity(userDto));
        List<Image> images = new ArrayList<>();
        if (items.isPresent()) {
            for (Item item : items.get()) {
                if (item.getConfirmUser() != null) {
                    images = imageRepository.findByItemIdx(item.getIdx());
                    ItemDto itemDto = converter.item_entityToDto(item);
                    UserDto confirmUser = converter.user_entityToDto(item.getConfirmUser());
                    CategoryDto categoryDto = converter.category_entityToDto(item.getCategory());
                    itemDto.setItemImages(images);
                    itemDto.setConfirmUser(converter.user_dtoToEntity(confirmUser));
                    itemDto.setSeller(converter.user_dtoToEntity(userDto));
                    itemDto.setCategory(converter.category_dtoToEntity(categoryDto));
                    itemDtos.add(itemDto);
                }
            }
        }
        return itemDtos;
    }

    @Transactional
    public List<ItemDto> b_history(UserDto userDto) {
        List<ItemDto> itemDtos = new ArrayList<>();
        Optional<List<Item>> items = itemRepository.findItemsByConfirmUser(converter.user_dtoToEntity(userDto));
        List<Image> images = new ArrayList<>();
        if (items.isPresent()) {
            for (Item item : items.get()) {
                if (item.getConfirmUser() != null) {
                    images = imageRepository.findByItemIdx(item.getIdx());
                    ItemDto itemDto = converter.item_entityToDto(item);
                    UserDto confirmUser = converter.user_entityToDto(item.getConfirmUser());
                    UserDto sellerDto = converter.user_entityToDto(item.getSeller());
                    CategoryDto categoryDto = converter.category_entityToDto(item.getCategory());
                    itemDto.setConfirmUser(converter.user_dtoToEntity(confirmUser));
                    itemDto.setItemImages(images);
                    itemDto.setSeller(converter.user_dtoToEntity(sellerDto));
                    itemDto.setCategory(converter.category_dtoToEntity(categoryDto));
                    itemDtos.add(itemDto);
                }
            }
        }
        return itemDtos;
    }
}
