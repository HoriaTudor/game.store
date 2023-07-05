package com.game.store.service;

import com.game.store.dto.UserDetailsDto;
import com.game.store.dto.UserDto;
import com.game.store.entity.User;
import com.game.store.mapper.UserMapper;
import com.game.store.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserMapper userMapper;
    public void saveUser(UserDto userDto) {
        User user = userMapper.map(userDto);
        userRepository.save(user);
    }

    public UserDetailsDto getUserDto(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        UserDetailsDto userDetailsDto = new UserDetailsDto();
        userDetailsDto.setFullName(user.get().getFullName());
        userDetailsDto.setAddress(user.get().getAddress());

        return userDetailsDto;
    }
}
