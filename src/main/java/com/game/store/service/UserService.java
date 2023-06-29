package com.game.store.service;

import com.game.store.dto.UserDto;
import com.game.store.entity.User;
import com.game.store.mapper.UserMapper;
import com.game.store.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
