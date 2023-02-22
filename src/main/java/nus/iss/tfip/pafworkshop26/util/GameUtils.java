package nus.iss.tfip.pafworkshop26.util;

import java.util.List;

import org.bson.Document;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import nus.iss.tfip.pafworkshop26.Constants;
import nus.iss.tfip.pafworkshop26.model.Comment;
import nus.iss.tfip.pafworkshop26.model.Game;

public class GameUtils implements Constants {

    public static Game documentToGame(Document doc) {
        Game game = new Game();
        game.setGame_id(doc.getInteger(FIELD_GID));
        game.setName(doc.getString(FIELD_NAME));
        if (doc.getInteger(FIELD_YEAR) != null) {
            game.setYear(doc.getInteger(FIELD_YEAR));
            ;
        }
        if (doc.getInteger(FIELD_RANKING) != null) {
            game.setRanking(doc.getInteger(FIELD_RANKING));
            ;
        }
        if (doc.getString(FIELD_AVERAGE) != null) {
            game.setAverage(doc.getString(FIELD_AVERAGE));
            ;
        }
        if (doc.getInteger(FIELD_USERS_RATED) != null) {
            game.setUsers_rated(doc.getInteger(FIELD_USERS_RATED));
            ;
        }
        if (doc.getString(FIELD_URL) != null) {
            game.setUrl(doc.getString(FIELD_URL));
            ;
        }
        if (doc.getString(FIELD_IMAGE) != null) {
            game.setImage(doc.getString(FIELD_IMAGE));
            ;
        }
        return game;
    }

    public static JsonArray gameToJson(List<Game> gameList) {
        JsonArrayBuilder jab = Json.createArrayBuilder();
        for (Game game : gameList) {
            JsonObjectBuilder job = Json.createObjectBuilder();
            if (game.getGame_id() != null) {
                job.add(FIELD_GID, game.getGame_id());
            }
            if (game.getName() != null) {
                job.add(FIELD_NAME, game.getName());
            }
            if (game.getYear() != null) {
                job.add(FIELD_YEAR, game.getYear());
            }
            if (game.getRanking() != null) {
                job.add(FIELD_RANKING, game.getRanking());
            }
            if (game.getAverage() != null) {
                job.add(FIELD_AVERAGE, game.getAverage());
            }
            if (game.getUsers_rated() != null) {
                job.add(FIELD_USERS_RATED, game.getUsers_rated());
            }
            if (game.getUrl() != null) {
                job.add(FIELD_URL, game.getUrl());
            }
            if (game.getImage() != null) {
                job.add(FIELD_IMAGE, game.getImage());
            }
            jab.add(job);
        }
        return jab.build();
    }

    public static JsonObject gameToJson(Game game) {
        JsonObjectBuilder job = Json.createObjectBuilder();
        if (game.getGame_id() != null) {
            job.add(FIELD_GID, game.getGame_id());
        }
        if (game.getName() != null) {
            job.add(FIELD_NAME, game.getName());
        }
        if (game.getYear() != null) {
            job.add(FIELD_YEAR, game.getYear());
        }
        if (game.getRanking() != null) {
            job.add(FIELD_RANKING, game.getRanking());
        }
        if (game.getAverage() != null) {
            job.add(FIELD_AVERAGE, game.getAverage());
        }
        if (game.getUsers_rated() != null) {
            job.add(FIELD_USERS_RATED, game.getUsers_rated());
        }
        if (game.getUrl() != null) {
            job.add(FIELD_URL, game.getUrl());
        }
        if (game.getImage() != null) {
            job.add(FIELD_IMAGE, game.getImage());
        }
        return job.build();
    }

    public static Document commentToDocument(Comment comment) {
        Document doc = new Document();
        doc.put(FIELD_C_ID, comment.getC_id());
        doc.put(FIELD_USER, comment.getUser());
        doc.put(FIELD_RATING, comment.getRating());
        doc.put(FIELD_C_TEXT, comment.getC_text());
        doc.put(FIELD_GID, comment.getGid());
        return doc;
    }
}
