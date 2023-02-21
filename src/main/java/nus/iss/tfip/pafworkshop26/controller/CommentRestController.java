package nus.iss.tfip.pafworkshop26.controller;

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

    @PostMapping(path = "/review", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<String> postMethodName(@RequestBody MultiValueMap<String, String> review) {
        // check if game exists
        if (review.getFirst(FIELD_NAME) != null) {
            int gid = gameSvc.getGIDByName(review.getFirst(FIELD_NAME));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Game name cannot be empty");
        }

        // TODO:


        return ResponseEntity
                .status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body("Comment inserted");
    }

}
