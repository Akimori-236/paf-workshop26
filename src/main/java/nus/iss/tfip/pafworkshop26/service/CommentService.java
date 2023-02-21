package nus.iss.tfip.pafworkshop26.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nus.iss.tfip.pafworkshop26.Constants;
import nus.iss.tfip.pafworkshop26.repository.CommentRepository;

@Service
public class CommentService implements Constants {

    @Autowired
    private CommentRepository commentRepo;

    public Integer getCIDByUser(String user) {
        return commentRepo.getCIDByUser(user);
    }
}
