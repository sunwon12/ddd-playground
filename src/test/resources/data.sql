INSERT INTO users (email, name, birth, gender, join_type, phone_number, authority, provider_name, provider_id,
                   profile_image)
VALUES ('test@test.com',
        'John Doe',
        '1990-01-01',
        'MALE',
        'KAKAO',
        '1234567890',
        'USER',
        'local',
        'kakao_test2',
        'https://example.com/profile_image.jpg');


INSERT INTO post (title, user_id, content)
VALUES ('포스트 제목', 1, '포스트 내용');

INSERT INTO comment (post_id, user_id, parent_comment_id, content, created_date)
VALUES (1, 1, null, '코멘트 내용', CURRENT_TIMESTAMP),
       (1, 1, null, '코멘트 내용', CURRENT_TIMESTAMP),
       (1, 1, null, '코멘트 내용', CURRENT_TIMESTAMP);


-- 대댓글 생성을 위한 MySQL insert 쿼리
INSERT INTO Comment (post_id, user_id, parent_comment_id, content, created_date)
VALUES (1, 1, 1, '대댓글 내용 1', CURRENT_TIMESTAMP - INTERVAL '5' HOUR),
       (1, 1, 1, '대댓글 내용 2', CURRENT_TIMESTAMP - INTERVAL '4' HOUR),
       (1, 1, 2, '대댓글 내용 3', CURRENT_TIMESTAMP - INTERVAL '2' HOUR),
       (1, 1, 2, '대댓글 내용 4', CURRENT_TIMESTAMP - INTERVAL '7' HOUR),
       (1, 1, 1, '대댓글 내용 5', CURRENT_TIMESTAMP - INTERVAL '2' HOUR);
