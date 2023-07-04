package com.game.store.repository;

import com.game.store.entity.ChosenGame;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChosenGameRepository extends JpaRepository<ChosenGame, Integer> {

}
