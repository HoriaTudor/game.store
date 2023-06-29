package com.game.store.service;

import com.game.store.entity.Game;
import com.game.store.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameService {
    @Autowired
    GameRepository gameRepository;

    public List<Game> listAllGames(){
        return gameRepository.findAll();
    }
}
