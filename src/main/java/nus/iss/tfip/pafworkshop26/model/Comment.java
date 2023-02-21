package nus.iss.tfip.pafworkshop26.model;

import lombok.Data;

@Data
public class Comment {
    private String c_id;
    private String user;
    private Integer rating;
    private String c_text;
    private Long gid;
}
