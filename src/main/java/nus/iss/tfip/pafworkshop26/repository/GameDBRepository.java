package nus.iss.tfip.pafworkshop26.repository;

import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import nus.iss.tfip.pafworkshop26.Constants;

@Repository
public class GameDBRepository implements Constants {

    @Autowired
    private MongoTemplate template;

    /*
     * db.game.find()
     * .projection({
     * gid: 1,
     * name: 1
     * })
     * .limit(?)
     * .skip(?)
     */
    public List<Document> getGameList(int limit, int offset) {
        Query query = new Query().limit(limit).skip(offset);

        // PROJECTIONS
        query.fields().include(FIELD_GID, FIELD_NAME);
        return template.find(query, Document.class, COLLECTION_GAME);
    }

    // db.game.countDocuments()
    public Long getTotalGames() {
        return template.count(new Query(), COLLECTION_GAME);
    }

    /*
     * db.game.find()
     * .projection({
     * gid: 1,
     * name: 1
     * })
     * .sort({ ranking: 1 })
     * .limit(?)
     * .skip(?);
     */
    public List<Document> getGameListByRank(int limit, int offset) {
        Sort sortRanking = Sort.by(Direction.ASC, FIELD_RANKING);
        Query query = new Query().with(sortRanking).limit(limit).skip(offset);

        // PROJECTIONS
        query.fields().include(FIELD_GID, FIELD_NAME);
        return template.find(query, Document.class, COLLECTION_GAME);
    }

    // db.game.find({"gid" : ???})
    public Document getGameByGID(Long gid) {
        Criteria criteria = Criteria.where(FIELD_GID).is(gid);
        Query query = new Query(criteria);
        return template.findOne(query, Document.class, COLLECTION_GAME);
    }

    // db.game.find({ "name": "???" }).projection({ gid: 1 })
    public Document getGIDByName(String name) {
        Criteria criteria = Criteria.where(FIELD_NAME).is(name);
        Query query = new Query(criteria);
        query.fields().include(FIELD_GID);
        return template.findOne(query, Document.class, COLLECTION_GAME);
    }
}
