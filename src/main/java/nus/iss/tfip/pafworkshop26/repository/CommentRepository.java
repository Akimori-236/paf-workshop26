package nus.iss.tfip.pafworkshop26.repository;

import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.aggregation.DateOperators.DateToString;
import org.springframework.data.mongodb.core.aggregation.DateOperators.Timezone;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update.PushOperatorBuilder;
import org.springframework.stereotype.Repository;

import com.mongodb.BasicDBObject;

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

    /*
db.comment.aggregate([
    { $sort: { rating: -1 } },
    {
        $lookup: {
            from: "game",
            localField: "gid",
            foreignField: "gid",
            as: "game"
        }
    },
    {
        $project: {
            _id: "$gid",
            name: { $arrayElemAt: ["$game.name", 0] },
            rating: "$rating",
            user: "$user",
            comment: "$c_text",
            review_id: "$c_id"
        }
    },
    { $limit: ? },
    {
        $group: {
            _id: 0,
            games: {
                $push: "$$ROOT"
            }
        }
    },
    {
        $project: {
            _id: 0,
            games: 1
        }
    }
])
     */
    public List<Document> getGamesByRating(String lowestHighest) throws RuntimeException {
        Long limit = 5L;
        // sort rating
        SortOperation sortRating = null;
        switch (lowestHighest.toLowerCase()) {
            case "lowest":
                sortRating = Aggregation.sort(Direction.ASC, FIELD_RATING);
                break;
            case "highest":
                sortRating = Aggregation.sort(Direction.DESC, FIELD_RATING);
                break;
            default:
                throw new RuntimeException("Use 'lowest' or 'highest' only");
        }
        // lookup game collection
        LookupOperation lookupGame = Aggregation.lookup(COLLECTION_GAME, FIELD_GID, FIELD_GID, COLLECTION_GAME);
        // project
        ProjectionOperation projectGame = Aggregation.project()
                .and(VALUE_GID).as(FIELD_OBJ_ID)
                .and(VALUE_RATING).as(FIELD_RATING)
                .and(VALUE_USER).as(FIELD_USER)
                .and(VALUE_C_TEXT).as(FIELD_COMMENT)
                .and(VALUE_C_ID).as(FIELD_REVIEW_ID)
                .and(ArrayOperators.arrayOf(VALUE_GAME_NAME).elementAt(0)).as(FIELD_NAME);
        // limit
        LimitOperation limitOp = Aggregation.limit(limit);
        // group
        GroupOperation groupComment = Aggregation.group()
                .push(Aggregation.ROOT).as(FIELD_GAMES);

        // get timestamp and set timezone to sg
        DateToString timenow = DateToString.dateOf(VALUE_DATENOW)
                .toString("%Y-%m-%d %H:%M:%S %z")
                .withTimezone(Timezone.fromZone(TimeZone.getTimeZone("Singapore")));
        // project
        ProjectionOperation project = Aggregation.project()
                .andExclude(FIELD_OBJ_ID)
                .andInclude(FIELD_GAMES);
        // .and(lowestHighest).as(FIELD_RATING)
        // .and(timenow).as(FIELD_TIMESTAMP);

        Aggregation pipeline = Aggregation.newAggregation(
            //  TODO:  take out projectGame, unwind and project?
                sortRating, lookupGame, projectGame, limitOp, groupComment, project);
        AggregationResults<Document> results = template.aggregate(
                pipeline, COLLECTION_GAME, Document.class);
        return results.getMappedResults();
    }

}
