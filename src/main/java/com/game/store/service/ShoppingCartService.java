package com.game.store.service;

import com.game.store.dto.ShoppingCartDto;
import com.game.store.dto.ShoppingCartGameDto;
import com.game.store.entity.ChosenGame;
import com.game.store.entity.ShoppingCart;
import com.game.store.repository.ShoppingCartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShoppingCartService {

    @Autowired
    ShoppingCartRepository shoppingCartRepository;

    public ShoppingCartDto getShoppingCartByUserEmail(String email){
        ShoppingCart shoppingCart = shoppingCartRepository.findByUserEmail(email);
        ShoppingCartDto shoppingCartDto = new ShoppingCartDto();
        Double subTotal = 0.0;

        for (ChosenGame chosenGame : shoppingCart.getChosenGames()) {
            ShoppingCartGameDto shoppingCartGameDto = ShoppingCartGameDto.builder()
                    .name(chosenGame.getGame().getTitle())
                    .price(String.valueOf(chosenGame.getGame().getPrice()))
                    .quantity(String.valueOf(chosenGame.getChosenQuantity())).build();

            Double auxPrice = chosenGame.getChosenQuantity() * chosenGame.getGame().getPrice();
            subTotal += auxPrice;

            shoppingCartGameDto.setTotal(String.valueOf(auxPrice));
            shoppingCartDto.add(shoppingCartGameDto);
        }

        shoppingCartDto.setSubTotal(String.valueOf(subTotal));
        shoppingCartDto.setTotal(String.valueOf(subTotal + 50));
        return shoppingCartDto;
    }
}
