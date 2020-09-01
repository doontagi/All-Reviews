package com.allreviews.controller;

import com.allreviews.Review;
import com.allreviews.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/review")
public class ReviewController {

    @Autowired
    ReviewService reviewService;

    @PostMapping
    public HttpStatus createReview(HttpSession session, @RequestBody Review review) {
        reviewService.addReview(review, (String) session.getAttribute("LOGIN_USER_ID"));
        return HttpStatus.OK;
    }

    @GetMapping
    public List<Review> getReview() {
        return reviewService.getReview();
    }
}
