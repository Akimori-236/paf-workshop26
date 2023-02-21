package nus.iss.tfip.pafworkshop26.util;

import java.util.List;

import org.bson.Document;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import nus.iss.tfip.pafworkshop26.model.Game;

public class GameUtils {

    public static Game toGame(Document doc) {
        Game g = new Game();
        g.setGame_id(doc.getInteger("gid"));
        g.setName(doc.getString("name"));
        return g;
    }

    public static JsonArray toJson(List<Game> gameList) {
        JsonArrayBuilder jab = Json.createArrayBuilder();
        for (Game g : gameList) {
            JsonObjectBuilder job = Json.createObjectBuilder();
            if (g.getGame_id() != null) {
                job.add("gid", g.getGame_id());
            }
            if (g.getName() != null) {
                job.add("name", g.getName());
            }
            if (g.getYear() != null) {
                job.add("year", g.getYear());
            }
            if (g.getRanking() != null) {
                job.add("ranking", g.getRanking());
            }
            if (g.getAverage() != null) {
                job.add("average", g.getAverage());
            }
            if (g.getUsers_rating() != null) {
                job.add("users_rating", g.getUsers_rating());
            }
            if (g.getUrl() != null) {
                job.add("url", g.getUrl());
            }
            if (g.getThumbnail() != null) {
                job.add("thumbnail", g.getThumbnail());
            }
            jab.add(job);
        }
        return jab.build();
    }
}
