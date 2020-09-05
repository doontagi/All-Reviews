package com.allreviews.controller;

import com.allreviews.Review;
import com.allreviews.aop.LoginCheck;
import com.allreviews.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    /**
     * 리뷰 생성.
     *
     * @author hanul
     *
     * @param session 사용자의 세션
     * @param review 생성하고자 하는 리뷰
     * @return Http 상태 코드
     */
    @PostMapping
    @LoginCheck
    public HttpStatus createReview(HttpSession session, @RequestBody Review review) {
        reviewService.addReview(review, (String) session.getAttribute("LOGIN_USER_ID"));
        return HttpStatus.OK;
    }

    /**
     * 리뷰 조회.
     *
     * @author hanul
     *
     * @return 모든 리뷰의 리스트
     */
    @GetMapping
    public List<Review> getReviews() {
        return reviewService.getReviews();
    }

    /**
     * 인기순 리뷰 조회.
     *
     * @author hanul
     *
     * @return 인기순으로 정렬된 모든 리뷰의 리스트
     */
    @GetMapping("/popular")
    public List<Review> getReviewsByLike() {
        return reviewService.getReviewsByLike();
    }

    /**
     * 특정 리뷰 조회.
     *
     * @author hanul
     *
     * @param id 조회하고자 하는 리뷰의 아이디
     * @return 조회하고자 하는 리뷰 객
     */
    @GetMapping("/{id}")
    public Review getReview(@PathVariable long id) {
       return reviewService.getReview(id);
    }

    /**
     * 특정 리뷰 삭제.
     *
     * @author hanul
     *
     * @param session 사용자의 세션
     * @param id 삭제하고자 하는 리뷰의 아이디
     */
    @DeleteMapping("/{id}")
    @LoginCheck
    public void deleteReview(HttpSession session, @PathVariable long id) {
        reviewService.deleteReview(id, (String) session.getAttribute("LOGIN_USER_ID"));
    }

}
