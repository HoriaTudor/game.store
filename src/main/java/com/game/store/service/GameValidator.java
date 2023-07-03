package com.game.store.service;

import com.game.store.dto.GameDto;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

@Service
public class GameValidator {

    public void validate(GameDto gameDto, BindingResult bindingResult) {
        if(gameDto.getTitle().isEmpty()){
            FieldError fieldError = new FieldError(
                    "gameDto",
                    "title",
                    "Please fill mandatory fields!");
            bindingResult.addError(fieldError);
        }
        if (Double.parseDouble(gameDto.getQuantity()) <= 0) {
            FieldError fieldError = new FieldError(
                    "gameDto",
                    "quantity",
                    "Please fill mandatory fields!"
            );
            bindingResult.addError(fieldError);
        }
    }
}
