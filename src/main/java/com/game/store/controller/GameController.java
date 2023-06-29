package com.game.store.controller;


import com.game.store.entity.Game;
import com.game.store.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class GameController {
    @Autowired
    GameService gameService;

    @GetMapping("/")
    public String viewTemplate(Model model) {

        boolean authenticated = SecurityContextHolder.getContext().getAuthentication().isAuthenticated();

        if(authenticated){
            List<Game> games = gameService.listAllGames();
            model.addAttribute("games", games);
//            ChosenItemDto chosenItemDto = new ChosenItemDto();
//            model.addAttribute("chosenItemDto", chosenItemDto);
            return "games";
        }else{
            return "login";
        }
    }
    @GetMapping("/games")
    public String viewAllGames(Model model) {
        List<Game> gameList = gameService.listAllGames();
        model.addAttribute("games", gameList);
        return "games";
    }
}
