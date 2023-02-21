package nus.iss.tfip.pafworkshop26.util;

import java.util.List;

import org.bson.Document;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import nus.iss.tfip.pafworkshop26.Constants;
import nus.iss.tfip.pafworkshop26.model.Game;

public class GameUtils implements Constants{

    public static Game toGame(Document doc) {
        Game g = new Game();
        g.setGame_id(doc.getInteger(FIELD_GID));
        g.setName(doc.getString(FIELD_NAME));
        if (doc.getInteger(FIELD_YEAR) != null) {
            g.setYear(doc.getInteger(FIELD_YEAR));
            ;
        }
        if (doc.getInteger(FIELD_RANKING) != null) {
            g.setRanking(doc.getInteger(FIELD_RANKING));
            ;
        }
        if (doc.getString(FIELD_AVERAGE) != null) {
            g.setAverage(doc.getString(FIELD_AVERAGE));
            ;
        }
        if (doc.getInteger(FIELD_USERS_RATED) != null) {
            g.setUsers_rated(doc.getInteger(FIELD_USERS_RATED));
            ;
        }
        if (doc.getString(FIELD_URL) != null) {
            g.setUrl(doc.getString(FIELD_URL));
            ;
        }
        if (doc.getString(FIELD_IMAGE) != null) {
            g.setImage(doc.getString(FIELD_IMAGE));
            ;
        }
        return g;
    }

    public static JsonArray toJson(List<Game> gameList) {
        JsonArrayBuilder jab = Json.createArrayBuilder();
        for (Game g : gameList) {
            JsonObjectBuilder job = Json.createObjectBuilder();
            if (g.getGame_id() != null) {
                job.add(FIELD_GID, g.getGame_id());
            }
            if (g.getName() != null) {
                job.add(FIELD_NAME, g.getName());
            }
            if (g.getYear() != null) {
                job.add(FIELD_YEAR, g.getYear());
            }
            if (g.getRanking() != null) {
                job.add(FIELD_RANKING, g.getRanking());
            }
            if (g.getAverage() != null) {
                job.add(FIELD_AVERAGE, g.getAverage());
            }
            if (g.getUsers_rated() != null) {
                job.add(FIELD_USERS_RATED, g.getUsers_rated());
            }
            if (g.getUrl() != null) {
                job.add(FIELD_URL, g.getUrl());
            }
            if (g.getImage() != null) {
                job.add(FIELD_IMAGE, g.getImage());
            }
            jab.add(job);
        }
        return jab.build();
    }

    public static JsonObject toJson(Game g) {
        JsonObjectBuilder job = Json.createObjectBuilder();
        if (g.getGame_id() != null) {
            job.add(FIELD_GID, g.getGame_id());
        }
        if (g.getName() != null) {
            job.add(FIELD_NAME, g.getName());
        }
        if (g.getYear() != null) {
            job.add(FIELD_YEAR, g.getYear());
        }
        if (g.getRanking() != null) {
            job.add(FIELD_RANKING, g.getRanking());
        }
        if (g.getAverage() != null) {
            job.add(FIELD_AVERAGE, g.getAverage());
        }
        if (g.getUsers_rated() != null) {
            job.add(FIELD_USERS_RATED, g.getUsers_rated());
        }
        if (g.getUrl() != null) {
            job.add(FIELD_URL, g.getUrl());
        }
        if (g.getImage() != null) {
            job.add(FIELD_IMAGE, g.getImage());
        }
        return job.build();
    }
}
