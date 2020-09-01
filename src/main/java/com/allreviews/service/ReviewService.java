package com.allreviews.service;

import com.allreviews.Review;
import com.allreviews.repository.ReviewRepository;
import com.allreviews.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ReviewService {

    @Autowired
    ReviewRepository reviewRepo;

    @Autowired
    UserRepository userRepo;

    public void addReview(Review review, String username) {
        review.setCreator(userRepo.findByUsername(username));
        reviewRepo.save(review);
    }

    public List<Review> getReview() {
        return reviewRepo.findAll();
    }

}
