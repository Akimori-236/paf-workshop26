package nus.iss.tfip.pafworkshop26.controller;

import java.time.Clock;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import nus.iss.tfip.pafworkshop26.model.Game;
import nus.iss.tfip.pafworkshop26.service.GameService;
import nus.iss.tfip.pafworkshop26.util.GameUtils;

import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping(path = { "/games", "/games/" })
public class GameController {

        @Autowired
        private GameService gameSvc;

        @GetMapping
        public ResponseEntity<String> getGameList(@RequestParam(defaultValue = "10") Integer limit,
                        @RequestParam(defaultValue = "0") Integer offset) {
                List<Game> gameList = gameSvc.getGameList(limit, offset);
                Long total = gameSvc.getTotalGames();
                String timestamp = ZonedDateTime.now(ZoneId.systemDefault())
                                .format(DateTimeFormatter.ofPattern("yyyy/MM/dd - HH:mm:ss"));
                JsonArray jArr = GameUtils.toJson(gameList);

                JsonObject jObj = Json.createObjectBuilder()
                                .add("games", jArr)
                                .add("offset", offset)
                                .add("limit", limit)
                                .add("total", total)
                                .add("timestamp", timestamp)
                                .build();

                return ResponseEntity
                                .status(HttpStatus.NOT_ACCEPTABLE)
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(jObj.toString());
        }

}