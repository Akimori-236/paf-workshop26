package nus.iss.tfip.pafworkshop26.controller;

import java.util.Date;
import java.text.SimpleDateFormat;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.Json;
import nus.iss.tfip.pafworkshop26.Constants;
import nus.iss.tfip.pafworkshop26.service.CommentService;
import nus.iss.tfip.pafworkshop26.util.GameUtils;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping(path = "/review")
public class CommentRestController implements Constants {

    @Autowired
    private CommentService commentSvc;

    /*
     * FORM INPUT
     * {user: <user name>
     * rating: <rating>
     * comment: <comment text>
     * ID: <gameid>
     * posted: <date>
     * name: <game name>}
     */
    @PostMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> postMethodName(@RequestBody MultiValueMap<String, String> review) {

        Document response = null;
        try {
            response = commentSvc.insertNewComment(review);
        } catch (RuntimeException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_ACCEPTABLE)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(GameUtils.generateErrorJson(e, HttpStatus.NOT_ACCEPTABLE));
        }
        String jsonStr = Json.createObjectBuilder()
                .add(FIELD_OBJ_ID, response.getObjectId(FIELD_OBJ_ID).toHexString())
                .add(FIELD_C_ID, response.getString(FIELD_C_ID))
                .build()
                .toString();

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(jsonStr);
    }

    /*
     * OUTPUT
     * {
     * comment: <user>
     * rating: <rating>
     * posted: <timestamp>
     * }
     */
    @PutMapping(path = "/{c_id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> putMethodName(@PathVariable String c_id,
            @RequestBody MultiValueMap<String, String> edits) {
        // TODO: process PUT request
        Document response = commentSvc.updateComment(c_id, edits);

        // create timestamp
        String timestamp = new SimpleDateFormat("yyyy/MM/dd  HH:mm:ss").format(new Date());
        // create json response
        String jsonStr = Json.createObjectBuilder()
                .add(FIELD_USER, response.getString(FIELD_USER))
                .add(FIELD_RATING, response.getInteger(FIELD_RATING))
                .add("posted", timestamp)
                .build()
                .toString();
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(jsonStr);
    }
}
