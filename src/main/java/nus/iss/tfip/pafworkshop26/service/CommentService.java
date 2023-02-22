package nus.iss.tfip.pafworkshop26.service;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nus.iss.tfip.pafworkshop26.Constants;
import nus.iss.tfip.pafworkshop26.model.Comment;
import nus.iss.tfip.pafworkshop26.repository.CommentRepository;
import nus.iss.tfip.pafworkshop26.util.GameUtils;

@Service
public class CommentService implements Constants {

    @Autowired
    private CommentRepository commentRepo;

    public String getCIDByUser(String user) {
        if (commentRepo.getCIDByUser(user) != null) {
            return commentRepo.getCIDByUser(user).getString(FIELD_C_ID);
        }
        return null;
    }

    public String insertNewComment(Comment comment) {
        Document doc = GameUtils.commentToDocument(comment);
        Document objId = commentRepo.insertNewComment(doc);
        ObjectId id = objId.getObjectId(FIELD_OBJ_ID);
        return id.toHexString();
    }
}
