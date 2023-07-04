package com.game.store.service;

import com.game.store.dto.ChosenGameDto;
import com.game.store.dto.GameDto;
import com.game.store.entity.ChosenGame;
import com.game.store.entity.Game;
import com.game.store.entity.User;
import com.game.store.repository.ChosenGameRepository;
import com.game.store.repository.GameRepository;
import com.game.store.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ChosenGameService {

    @Autowired
    ChosenGameRepository chosenGameRepository;

    @Autowired
    GameRepository gameRepository;

    @Autowired
    UserRepository userRepository;

    public void addToCart(ChosenGameDto chosenGameDto, String gameId, String email) {
        ChosenGame chosenGame = buildProduct(chosenGameDto, gameId, email);
        chosenGameRepository.save(chosenGame);
    }

    private ChosenGame buildProduct(ChosenGameDto chosenGameDto, String gameId, String email) {
        ChosenGame chosenGame = new ChosenGame();
        chosenGame.setChosenQuantity(Integer.parseInt(chosenGameDto.getQuantity()));

        Optional<Game> game = gameRepository.findById(Integer.parseInt(gameId));
        game.ifPresent(chosenGame::setGame);

        Optional<User> user = userRepository.findByEmail(email);
        user.ifPresent(value -> chosenGame.setShoppingCart(value.getShoppingCart()));

        return chosenGame;
    }
}
