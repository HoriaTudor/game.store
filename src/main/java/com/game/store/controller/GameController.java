package com.game.store.controller;

import com.game.store.dto.GameDto;
import com.game.store.entity.Game;
import com.game.store.service.GameService;
import com.game.store.service.GameValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
public class GameController {

    @Autowired
    GameService gameService;

    @Autowired
    private GameValidator gameValidator;


    @GetMapping("/")
    public String viewTemplate(Model model) {

        boolean authenticated = SecurityContextHolder.getContext().getAuthentication().isAuthenticated();

        if (authenticated) {
            List<Game> games = gameService.listAllGames();
            model.addAttribute("games", games);
//            ChosenItemDto chosenItemDto = new ChosenItemDto();
//            model.addAttribute("chosenItemDto", chosenItemDto);
            return "games";
        } else {
            return "login";
        }
    }

    @GetMapping("/games")
    public String viewAllGames(Model model) {
        List<Game> gameList = gameService.listAllGames();
        model.addAttribute("games", gameList);
        return "games";
    }

    @GetMapping("/game")
    public String viewGameForm(Model model) {
        GameDto gameDto = new GameDto();
        model.addAttribute("game", gameDto);

        return "game";
    }

    @PostMapping("/game/save")
    public String saveGame(
            @ModelAttribute("game") GameDto gameDto,
            BindingResult bindingResult,
            Model model,
            @RequestParam("coverImage") MultipartFile file
    ) throws IOException {
            gameValidator.validate(gameDto, bindingResult);
            if (bindingResult.hasErrors()) {
                model.addAttribute("game", gameDto);
                return "game";
            }
            gameService.saveGame(gameDto, file);
            List<Game> list = gameService.listAllGames();
            model.addAttribute("games", list);

            return "redirect:/games";
    }
}
