package nus.iss.tfip.pafworkshop26.model;

import lombok.Data;

@Data
public class Game {
    private Integer game_id;
    private String name;
    private Integer year;
    private Integer ranking;
    private String average;
    private Integer users_rated;
    private String url;
    private String image;
}
