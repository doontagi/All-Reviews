package com.allreviews.service;

import com.allreviews.Review;
import com.allreviews.User;
import com.allreviews.repository.ReviewRepository;
import com.allreviews.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;

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

    public void deleteReview(long index, String username) {
       Review review = reviewRepo.findById(index).get();
       User creator = review.getCreator();
       if (!creator.getUsername().equals(username)) {
           throw new HttpStatusCodeException(HttpStatus.UNAUTHORIZED, "Only creator can delete his review") {};
       }
       reviewRepo.delete(review);
    }

}
