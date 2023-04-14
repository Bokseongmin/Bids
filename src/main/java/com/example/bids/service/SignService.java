package com.example.bids.service;

import com.example.bids.entity.User;
import com.example.bids.entity.UserDto;
import com.example.bids.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class SignService {
    private final UserRepository userRepository;

    public void signUp(Map<String, String> userData) {
        UserDto userDto = UserDto.builder()
                .userName(userData.get("userName"))
                .email(userData.get("email"))
                .userPw(userData.get("userPw"))
                .phoneNo(userData.get("phoneNo"))
                .addrCode(userData.get("addrCode")).addr1(userData.get("addr1")).addr2(userData.get("addr2")).addr3(userData.get("addr3"))
                .build();

        userRepository.save(dtoToEntity(userDto));
    }

    public boolean emailCheck(Map<String, String> userData) {
        Optional<User> user = userRepository.findByEmail(userData.get(("email")));
        if(user.isPresent()) {
            return true;
        }
        return false;
    }

    public UserDto signIn(Map<String, String> userData) {
        Optional<User> user = userRepository.findByEmailAndUserPw(userData.get("email"), userData.get("userPw"));
        if(user.isPresent()) {
            return  entityToDto(user.get());
        }
        return null;
    }

    public User getEntity(UserDto userDto) {
        return dtoToEntity(userDto);
    }

    private UserDto entityToDto(User user) {
        var dto = UserDto.builder()
                .userName(user.getUserName())
                .email(user.getEmail())
                .userPw(user.getUserPw())
                .phoneNo(user.getPhoneNo())
                .addrCode(user.getAddrCode()).addr1(user.getAddr1()).addr2(user.getAddr2()).addr3(user.getAddr3())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .idx(user.getIdx())
                .build();
        return dto;
    }

    private User dtoToEntity(UserDto userDto) {
        var dto = User.builder()
                .userName(userDto.getUserName())
                .email(userDto.getEmail())
                .userPw(userDto.getUserPw())
                .phoneNo(userDto.getPhoneNo())
                .addrCode(userDto.getAddrCode()).addr1(userDto.getAddr1()).addr2(userDto.getAddr2()).addr3(userDto.getAddr3())
                .build();
        return dto;
    }
}
