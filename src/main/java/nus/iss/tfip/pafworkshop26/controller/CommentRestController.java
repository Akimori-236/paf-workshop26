package nus.iss.tfip.pafworkshop26.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import nus.iss.tfip.pafworkshop26.Constants;
import nus.iss.tfip.pafworkshop26.model.Comment;
import nus.iss.tfip.pafworkshop26.service.CommentService;
import nus.iss.tfip.pafworkshop26.service.GameService;

@RestController
public class CommentRestController implements Constants {

    @Autowired
    private GameService gameSvc;
    @Autowired
    private CommentService commentSvc;

    /*
     * {user: <user name>
     * rating: <rating>
     * comment: <comment text>
     * ID: <gameid>
     * posted: <date>
     * name: <game name>}
     */
    @PostMapping(path = "/review", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<String> postMethodName(@RequestBody MultiValueMap<String, String> review) {
        // check if game exists
        Long gid = null;
        gid = gameSvc.getGIDByName(review.getFirst(FIELD_NAME));
        if (gid != Long.parseLong(review.getFirst("ID"))) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Game / gameID doesn't tally");
        }
        // check if user exists
        String c_id = null;
        c_id = commentSvc.getCIDByUser(review.getFirst(FIELD_USER));
        if (c_id == null) {
            c_id = UUID.randomUUID().toString().substring(0, 8);
        }
        //
        Comment comm = Comment.create(c_id, review.getFirst(FIELD_USER),
                Integer.parseInt(review.getFirst(FIELD_RATING)), review.getFirst(FIELD_COMMENT), gid);
        if (comm == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_ACCEPTABLE)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body("Comment not inserted");
        }
        String objId = null;
        objId = commentSvc.insertNewComment(comm);
        if (objId == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_ACCEPTABLE)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body("Comment not inserted");
        }

        String jsonStr = "{\"CommentObjId\": %s}".format(objId);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(jsonStr);
    }

}
