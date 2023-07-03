package com.game.store.service;

import com.game.store.dto.GameDto;
import com.game.store.entity.Game;
import com.game.store.mapper.GameMapper;
import com.game.store.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class GameService {

    @Autowired
    GameRepository gameRepository;

    @Autowired
    private GameMapper gameMapper;

    public List<Game> listAllGames(){

        return gameRepository.findAll();
    }

    public void saveGame(GameDto gameDto, MultipartFile file) throws IOException {
        Game game = gameMapper.gameMapper(gameDto, file);
        gameRepository.save(game);
    }
}
