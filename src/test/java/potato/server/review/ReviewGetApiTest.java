package potato.server.review;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import potato.server.common.WithMockCustomMember;
import potato.server.product.domain.Product;
import potato.server.product.repository.ProductRepository;
import potato.server.review.domain.Review;
import potato.server.review.repository.ReviewRepository;
import potato.server.review.spec.ChoicedExperience;
import potato.server.user.domain.User;
import potato.server.user.repository.UserRepository;
import potato.server.user.spec.Gender;
import potato.server.user.spec.JoinType;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author 정순원
 * @since 2024-01-16
 */
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class ReviewGetApiTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ReviewRepository reviewRepository;
    @Autowired
    ProductRepository productRepository;

    Product product;

    @BeforeEach
    void init() {
        User user = User.builder()
                .name("sun won")
                .email("test@test.com")
                .providerId("kakao_test")
                .joinType(JoinType.KAKAO)
                .gender(Gender.MALE)
                .providerName("kakao")
                .phoneNumber("123-1234-1234")
                .profileImage("imageURL")
                .build();

        User user2 = User.builder()
                .name("min seok")
                .email("test2@test.com")
                .providerId("kakao_test2")
                .joinType(JoinType.KAKAO)
                .gender(Gender.MALE)
                .providerName("kakao")
                .phoneNumber("123-1234-5555")
                .profileImage("imageURL")
                .build();

        userRepository.save(user);
        userRepository.save(user2);

        product = productRepository.save(Product.builder()
                .title("사과")
                .price(BigDecimal.valueOf(10000))
                .description("맛있는 사과")
                .build());

        Review review = Review.builder()
                .user(user)
                .product(product)
                .content("좋아요")
                .evaluation(5)
                .choicedExperience(ChoicedExperience.MEANINGFUL)
                .build();

        Review review2 = Review.builder()
                .user(user2)
                .product(product)
                .content("짱 좋아요")
                .evaluation(4)
                .choicedExperience(ChoicedExperience.NOTHING)
                .build();

        reviewRepository.save(review);
        reviewRepository.save(review2);


    }

    @DisplayName("특정 품목 리뷰 조회")
    @Test
    @Transactional
    @WithMockCustomMember
    void test1() throws Exception {

        Long productId = product.getId();
        mockMvc.perform(get("/api/v1/reviews/all/" + productId))
                .andExpect(status().isOk())
                .andDo(print());
    }

}
