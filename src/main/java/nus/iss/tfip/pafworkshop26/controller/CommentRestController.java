package nus.iss.tfip.pafworkshop26.controller;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.Json;
import nus.iss.tfip.pafworkshop26.Constants;
import nus.iss.tfip.pafworkshop26.service.CommentService;

@RestController
public class CommentRestController implements Constants {

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

        Document response = null;
        try {
            response = commentSvc.insertNewComment(review);
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_ACCEPTABLE)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(e.getMessage());
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

}
