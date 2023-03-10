package nus.iss.tfip.pafworkshop26.model;

import lombok.Data;

@Data
public class Comment {
    private String c_id;
    private String user;
    private Integer rating;
    private String c_text;
    private Long gid;

    public static Comment create(String c_id, String user, int rating, String c_text, Long gid) throws RuntimeException {
        Comment c = new Comment();
        c.setC_id(c_id);
        c.setUser(user);
        if (rating >= 0 && rating <= 10) {
            c.setRating(rating);
        } else {
            throw new RuntimeException("Rating must be between 0 and 10");
        }
        c.setC_text(c_text);
        c.setGid(gid);
        return c;
    }
}
