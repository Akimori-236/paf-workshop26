package nus.iss.tfip.pafworkshop26.model;

import lombok.Data;

@Data
public class Game {
    private Integer game_id;
    private String name;
    private String year;
    private String ranking;
    private String average;
    private String users_rating;
    private String url;
    private String thumbnail;
}
