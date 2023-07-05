package com.game.store.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ShoppingCartDto {

    private String subTotal;
    private String total;
    List<ShoppingCartGameDto> games = new ArrayList<>();

    public void add(ShoppingCartGameDto shoppingCartGameDto) {
        games.add(shoppingCartGameDto);
    }
}
