package com.game.store.mapper;

import com.game.store.dto.UserDto;
import com.game.store.entity.Role;
import com.game.store.entity.ShoppingCart;
import com.game.store.entity.User;
import com.game.store.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class UserMapper {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    public User map(UserDto userDto) {
        User user = new User();
        user.setFullName(userDto.getFullName());
        user.setEmail(userDto.getEmail());
        String passwordEncoded = passwordEncoder.encode(userDto.getPassword());
        user.setPassword(passwordEncoded);
        user.setAddress(userDto.getAddress());
        Optional<Role> role = roleRepository.findByName(userDto.getUserRole());
        Role role1 = new Role();
        if(role.isEmpty()){
            role1 = saveRole(userDto);
            user.setRoles(List.of(role1));
        } else {
            user.setRoles(List.of(role.get()));
        }

        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setUser(user);
        user.setShoppingCart(shoppingCart);

        return user;
    }

    private Role saveRole(UserDto userDto) {
        Role role = new Role();
        role.setName(userDto.getUserRole());
        return roleRepository.save(role);
    }
}
