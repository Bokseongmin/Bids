package com.example.bids.service;

import com.example.bids.entity.User;
import com.example.bids.entity.UserDto;
import com.example.bids.repository.UserRepository;
import com.example.bids.utill.Converter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class SignService {
    private final UserRepository userRepository;
    private final Converter converter;

    public void signUp(Map<String, String> userData) {
        UserDto userDto = UserDto.builder()
                .userName(userData.get("userName"))
                .email(userData.get("email"))
                .userPw(userData.get("userPw"))
                .phoneNo(userData.get("phoneNo"))
                .addrCode(userData.get("addrCode")).addr1(userData.get("addr1")).addr2(userData.get("addr2")).addr3(userData.get("addr3"))
                .build();

        userRepository.save(converter.user_dtoToEntity(userDto));
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
            return  converter.user_entityToDto(user.get());
        }
        return null;
    }
}
