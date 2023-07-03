package com.game.store.mapper;

import com.game.store.dto.GameDto;
import com.game.store.entity.FileCover;
import com.game.store.entity.Game;
import com.game.store.repository.FileCoverRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@Component
public class GameMapper {

    private static String DIRECTORY = System.getProperty("user.dir") + "/src/main/resources/static/img/";

    @Autowired
    private FileCoverRepository fileCoverRepository;

    public Game gameMapper(GameDto gameDto, MultipartFile file) throws IOException {
        Path fileName = Paths.get(DIRECTORY);
        FileCover fileCover = new FileCover();
        fileCover.setPath(fileName.toFile().getPath());
        FileCover fileSaved = fileCoverRepository.save(fileCover);

        final String fileExtension = Optional.ofNullable(file.getOriginalFilename())
                .flatMap(GameMapper::getFileExtension)
                .orElse("");

        final String targetFileName = fileSaved.getId() + "." + fileExtension;
        final Path targetPath = fileName.resolve(targetFileName);

        File f = new File(String.valueOf(targetPath));
        file.transferTo(f);

        Game game = new Game();
        game.setTitle(gameDto.getTitle());
        game.setDescription(gameDto.getDescription());
        game.setPrice(Double.parseDouble(gameDto.getPrice()));
        game.setQuantity(Integer.parseInt((gameDto.getQuantity())));
        game.setFileCover(fileSaved);

        return game;
    }

    private static Optional<String> getFileExtension(String fileName) {
        final int indexOfLastDot = fileName.lastIndexOf('.');

        if (indexOfLastDot == -1) {
            return Optional.empty();
        } else {
            return Optional.of(fileName.substring(indexOfLastDot + 1));
        }
    }
}
