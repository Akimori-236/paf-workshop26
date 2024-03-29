package nus.iss.tfip.pafworkshop26.controller;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import nus.iss.tfip.pafworkshop26.model.Game;
import nus.iss.tfip.pafworkshop26.service.GameService;
import nus.iss.tfip.pafworkshop26.service.CommentService;
import nus.iss.tfip.pafworkshop26.util.GameUtils;

import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping(path = { "/games", "/games/" })
@CrossOrigin(origins = "*")
public class GameRestController {

        @Autowired
        private GameService gameSvc;
        @Autowired
        private CommentService commentSvc;

        @GetMapping
        public ResponseEntity<String> getGameList(@RequestParam(defaultValue = "10") Integer limit,
                        @RequestParam(defaultValue = "0") Integer offset) {
                List<Game> gameList = gameSvc.getGameList(limit, offset);
                // query total sum
                Long total = gameSvc.getTotalGames();
                // get current time
                String timestamp = ZonedDateTime.now(ZoneId.systemDefault())
                                .format(DateTimeFormatter.ofPattern("yyyy/MM/dd - HH:mm:ss"));
                // convert game list to json array
                JsonArray jArr = GameUtils.gameToJson(gameList);
                // build json response
                JsonObject jObj = Json.createObjectBuilder()
                                .add("games", jArr)
                                .add("offset", offset)
                                .add("limit", limit)
                                .add("total", total)
                                .add("timestamp", timestamp)
                                .build();

                return ResponseEntity
                                .status(HttpStatus.OK)
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(jObj.toString());
        }

        @GetMapping(path = { "/rank", "/rank/" })
        public ResponseEntity<String> getGameListByRank(@RequestParam(defaultValue = "10") Integer limit,
                        @RequestParam(defaultValue = "0") Integer offset) {
                List<Game> gameList = gameSvc.getGameListByRank(limit, offset);
                // query total sum
                Long total = gameSvc.getTotalGames();
                // get current time
                String timestamp = ZonedDateTime.now(ZoneId.systemDefault())
                                .format(DateTimeFormatter.ofPattern("yyyy/MM/dd - HH:mm:ss"));
                // convert game list to json array
                JsonArray jArr = GameUtils.gameToJson(gameList);
                // build json response
                JsonObject jObj = Json.createObjectBuilder()
                                .add("games", jArr)
                                .add("offset", offset)
                                .add("limit", limit)
                                .add("total", total)
                                .add("timestamp", timestamp)
                                .build();

                return ResponseEntity
                                .status(HttpStatus.OK)
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(jObj.toString());
        }

        @GetMapping(path = "/{game_id}")
        public ResponseEntity<String> getGameByGID(@PathVariable Long game_id) {
                Game game = gameSvc.getGameByGID(game_id);

                // build json response
                JsonObject jObj = GameUtils.gameToJson(game);

                return ResponseEntity
                                .status(HttpStatus.OK)
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(jObj.toString());
        }

        // Workshop28(a)
        // Test return document
        @GetMapping(path = "/{game_id}/reviews")
        public ResponseEntity<Document> getCommentsByGID(@PathVariable Long game_id) {
                Document doc = gameSvc.getCommentsByGID(game_id);

                return ResponseEntity
                                .status(HttpStatus.OK)
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(doc);
        }

        // Workshop28(b)
        @GetMapping(path = "/rank/{lowestHighest}")
        public ResponseEntity<List<Document>> getGamesByRating(@PathVariable String lowestHighest) {
                // check if lowest/highest
                if (!(lowestHighest.equalsIgnoreCase("lowest") || lowestHighest.equalsIgnoreCase("highest"))) {
                        return ResponseEntity
                                        .status(HttpStatus.NOT_ACCEPTABLE)
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .body(null);
                }
                List<Document> doc = commentSvc.getGamesByRating(lowestHighest);
                return ResponseEntity
                                .status(HttpStatus.OK)
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(doc);
        }

}
