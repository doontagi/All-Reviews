package com.allreviews.service;

import com.allreviews.Review;
import com.allreviews.User;
import com.allreviews.repository.ReviewRepository;
import com.allreviews.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
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

    /**
     * 새로운 리뷰 추가.
     *
     * @author hanul
     *
     * @param review 새로 생성될 review
     * @param username review를 새로 생성한 user의 username
     */
    public void addReview(Review review, String username) {
        review.setCreator(userRepo.findByUsername(username));
        reviewRepo.save(review);
    }

    /**
     * 모든 리뷰 조회.
     *
     * @author hanul
     *
     * @return 모든 리뷰의 리스트
     */
    public List<Review> getReviews() {
        return reviewRepo.findAll();
    }

    /**
     * 특정 리뷰 조회.
     *
     * @author hanul
     *
     * @param index 조회할 리뷰의 id
     * @return 해당 id를 가지고 있는 리뷰 객체
     */
    public Review getReview(Long index) {
        return reviewRepo.findById(index).get();
    }

    /**
     * 인기 순으로 정렬된 모든 리뷰 조회.
     *
     * @author hanul
     *
     * @return username 인기순으로 정렬된 리뷰의 리스
     */
    @Cacheable(value = "reviewsByLike")
    public List<Review> getReviewsByLike() {
        return reviewRepo.findAllByOrderByLikesDesc();

    }

    /**
     * 특정 review 삭제.
     *
     * @author hanul
     *
     * @param index 삭제할 리뷰의 id
     * @param username 삭제를 요청한 user의 username
     */
    public void deleteReview(long index, String username) {
       Review review = reviewRepo.findById(index).get();
       User creator = review.getCreator();
       if (!creator.getUsername().equals(username)) {
           throw new HttpStatusCodeException(HttpStatus.UNAUTHORIZED,
                   "Only creator can delete his review") { };
       }
       reviewRepo.delete(review);
    }
}
