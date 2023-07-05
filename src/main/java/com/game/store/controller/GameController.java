package com.game.store.controller;

import com.game.store.dto.ChosenGameDto;
import com.game.store.dto.GameDto;
import com.game.store.dto.ShoppingCartDto;
import com.game.store.dto.UserDetailsDto;
import com.game.store.entity.Game;
import com.game.store.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
public class GameController {

    @Autowired
    GameService gameService;

    @Autowired
    private GameValidator gameValidator;

    @Autowired
    private ChosenGameService chosenGameService;

    @Autowired
    private ShoppingCartService shoppingCartService;

    @Autowired
    private UserService userService;

    @Autowired
    private CustomerOrderService customerOrderService;

    @GetMapping("/")
    public String viewTemplate(Model model) {

        boolean authenticated = SecurityContextHolder.getContext().getAuthentication().isAuthenticated();

        if (authenticated) {
            List<Game> games = gameService.listAllGames();
            model.addAttribute("games", games);
            ChosenGameDto chosenGameDto = ChosenGameDto.builder().build();
            model.addAttribute("chosenGameDto", chosenGameDto);
            return "games";
        } else {
            return "login";
        }
    }

    @GetMapping("/games")
    public String viewAllGames(Model model) {
        List<Game> gameList = gameService.listAllGames();
        model.addAttribute("games", gameList);
        ChosenGameDto chosenGameDto = ChosenGameDto.builder().build();
        model.addAttribute("chosenGameDto", chosenGameDto);
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
        ChosenGameDto chosenGameDto = ChosenGameDto.builder().build();
        model.addAttribute("chosenGameDto", chosenGameDto);

        return "redirect:/games";
    }

    @PostMapping("/game/{gameId}")
    public String addToShoppingList(@PathVariable(value = "gameId") String gameId,
                                    @ModelAttribute ChosenGameDto chosenGameDto,
                                    BindingResult bindingResult,
                                    Model model) {
        model.addAttribute("chosenGameDto", chosenGameDto);
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        chosenGameService.addToCart(chosenGameDto, gameId, email);

        return "redirect:/cart";
    }

    @GetMapping("/cart")
    public String getCart(Model model) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        ShoppingCartDto shoppingCartDto = shoppingCartService.getShoppingCartByUserEmail(email);
        model.addAttribute("shoppingCartDto", shoppingCartDto);

        return "cart";
    }

    @GetMapping("/checkout")
    public String getCheckout(Model model){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        ShoppingCartDto shoppingCartDto = shoppingCartService.getShoppingCartByUserEmail(email);
        model.addAttribute("shoppingCartDto", shoppingCartDto);
        UserDetailsDto userDetailsDto = userService.getUserDto(email);
        model.addAttribute("userDetailsDto", userDetailsDto);

        return "checkout";
    }

    @PostMapping("/sendOrder")
    public String sendOrder(@ModelAttribute("userDetailsDto") UserDetailsDto userDetailsDto) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        customerOrderService.addCustomerOrder(email, userDetailsDto.getShippingAddress());

        return "confirmation";
    }
}
