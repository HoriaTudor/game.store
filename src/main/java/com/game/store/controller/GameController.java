package com.game.store.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GameController {

    @GetMapping("/")
    public String viewTemplate() {

        return "login";
    }
}
