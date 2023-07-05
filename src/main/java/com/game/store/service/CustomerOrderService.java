package com.game.store.service;

import com.game.store.entity.ChosenGame;
import com.game.store.entity.CustomerOrder;
import com.game.store.entity.ShoppingCart;
import com.game.store.entity.User;
import com.game.store.repository.ChosenGameRepository;
import com.game.store.repository.CustomerOrderRepository;
import com.game.store.repository.ShoppingCartRepository;
import com.game.store.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerOrderService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    CustomerOrderRepository customerOrderRepository;

    @Autowired
    ShoppingCartRepository shoppingCartRepository;

    @Autowired
    ChosenGameRepository chosenGameRepository;

    public void addCustomerOrder(String email, String shippingAddress) {
        Optional<User> user = userRepository.findByEmail(email);
        CustomerOrder customerOrder = new CustomerOrder();
        customerOrder.setUser(user.get());
        customerOrder.setShippingAddress(shippingAddress);

        customerOrderRepository.save(customerOrder);
        ShoppingCart shoppingCart = shoppingCartRepository.findByUserEmail(email);
        for (ChosenGame chosenGame : shoppingCart.getChosenGames()) {
            chosenGame.setShoppingCart(null);
            chosenGame.setCustomerOrder(customerOrder);
            chosenGameRepository.save(chosenGame);
        }

    }
}
