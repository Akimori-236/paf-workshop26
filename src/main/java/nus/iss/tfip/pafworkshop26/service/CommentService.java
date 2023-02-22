package nus.iss.tfip.pafworkshop26.service;

import java.util.UUID;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import nus.iss.tfip.pafworkshop26.Constants;
import nus.iss.tfip.pafworkshop26.model.Comment;
import nus.iss.tfip.pafworkshop26.repository.CommentRepository;
import nus.iss.tfip.pafworkshop26.util.GameUtils;

@Service
public class CommentService implements Constants {

    @Autowired
    private CommentRepository commentRepo;

    @Autowired
    private GameService gameSvc;

    public String getCIDByUser(String user) {
        if (commentRepo.getCIDByUser(user) != null) {
            return commentRepo.getCIDByUser(user).getString(FIELD_C_ID);
        }
        return null;
    }

    public Document insertNewComment(MultiValueMap<String, String> review) throws Exception {
        // check if game exists
        Long gid = null;
        gid = gameSvc.getGIDByName(review.getFirst(FIELD_NAME));
        if (gid != Long.parseLong(review.getFirst("ID"))) {
            throw new RuntimeException("GameID and GameName does not tally");
        }
        // check if user exists
        String c_id = null;
        c_id = this.getCIDByUser(review.getFirst(FIELD_USER));
        if (c_id == null) {
            c_id = UUID.randomUUID().toString().substring(0, 8);
        }
        //
        Comment comment = Comment.create(c_id, review.getFirst(FIELD_USER),
                Integer.parseInt(review.getFirst(FIELD_RATING)), review.getFirst(FIELD_COMMENT), gid);

        Document doc = GameUtils.commentToDocument(comment);
        Document response = commentRepo.insertNewComment(doc);
        response.put(FIELD_C_ID, c_id);
        return response;
    }
}
