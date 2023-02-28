package nus.iss.tfip.pafworkshop26.repository;

import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.LookupOperation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;
import org.springframework.data.mongodb.core.aggregation.AddFieldsOperation.AddFieldsOperationBuilder;
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

    /*
     * db.game.aggregate([
     * { $match: { gid: 1 } },
     * {
     * $lookup: {
     * from: "comment",
     * localField: "gid",
     * foreignField: "gid",
     * as: "comment "
     * }
     * },
     * {
     * $project: {
     * _id: 0,
     * gid: 1,
     * name: 1,
     * year: 1,
     * rank: "$ranking",
     * users_rated: 1,
     * url: 1,
     * thumbnail: "$image",
     * comments: "$comment.c_id"
     * }
     * }
     * ])
     */
    public Document getCommentsByGID(Long gid) {
        Criteria criteria = Criteria.where(FIELD_GID).is(gid);
        // $match
        MatchOperation matchGID = Aggregation.match(criteria);
        // $lookup
        LookupOperation lookupCID = Aggregation.lookup(
                COLLECTION_COMMENT, FIELD_GID, FIELD_GID, FIELD_COMMENT);
        // $project
        ProjectionOperation project = Aggregation.project(
                FIELD_GID,
                FIELD_NAME,
                FIELD_YEAR,
                FIELD_USERS_RATED,
                FIELD_URL)
                .andExclude(FIELD_OBJ_ID)
                .and(FIELD_RANKING).as(FIELD_RANK)
                .and(FIELD_IMAGE).as(FIELD_THUMBNAIL)
                .and(VALUE_COMMENT_CID).as(FIELD_COMMENT)
                .and(VALUE_NEWDATE).as(FIELD_TIMESTAMP);

        Aggregation pipeline = Aggregation.newAggregation(
                matchGID, lookupCID, project);
        AggregationResults<Document> results = template.aggregate(
                pipeline, COLLECTION_GAME, Document.class);
        return results.getUniqueMappedResult();
    }
}
