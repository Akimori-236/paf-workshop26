package nus.iss.tfip.pafworkshop26.repository;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import nus.iss.tfip.pafworkshop26.Constants;

@Repository
public class CommentRepository implements Constants {

    @Autowired
    private MongoTemplate template;

    public Document getCIDByUser(String user) {
        Criteria criteria = Criteria.where(FIELD_USER).is(user);
        Query query = new Query(criteria);
        query.fields().include(FIELD_C_ID);
        return template.findOne(query, Document.class, COLLECTION_COMMENT);
    }

    public Document insertNewComment(Document doc) {
        Document objId = template.insert(doc, COLLECTION_COMMENT);
        return objId;
    }

    public Boolean isCommentExists(String cid) {
        Criteria criteria = Criteria.where(FIELD_C_ID).is(cid);
        Query query = new Query(criteria);
        Document objId = template.findOne(query, Document.class, COLLECTION_COMMENT);
        return objId != null;
    }

    public Document updateComment(String cid, Document c) {
        return null;
    }
}
