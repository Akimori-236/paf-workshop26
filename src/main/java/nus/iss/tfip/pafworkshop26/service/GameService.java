package nus.iss.tfip.pafworkshop26.service;

import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nus.iss.tfip.pafworkshop26.model.Game;
import nus.iss.tfip.pafworkshop26.repository.GameDBRepository;
import nus.iss.tfip.pafworkshop26.util.GameUtils;

@Service
public class GameService {
    @Autowired
    private GameDBRepository gameRepo;

    public List<Game> getGameList(int limit, int offset) {
        List<Document> docList = gameRepo.getGames(limit, offset);
        List<Game> gameList = docList.stream()
                .map(v -> GameUtils.toGame(v))
                .toList();
        return gameList;
    }

    public Long getTotalGames() {
        return gameRepo.getTotalGames();
    }
}
