package potato.server.community.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import potato.server.common.BaseTimeEntity;
import potato.server.common.CustomException;
import potato.server.common.ResultCode;
import potato.server.user.domain.User;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    public String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(columnDefinition = "TEXT")
    public String content;

    @Builder
    public Post(String title, User user, String content) {
        this.title = title;
        this.user = user;
        this.content = content;
    }

    public void updatePost(User user, String title, String content) {
        if (!this.user.getId().equals(user.getId())) {
            throw new CustomException(HttpStatus.FORBIDDEN, ResultCode.USER_NOT_PERMISSION);
        }

        this.title = title;
        this.content = content;
    }
}
