package potato.server.community.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import potato.server.common.WithMockCustomMember;
import potato.server.community.dto.request.CommentRequest;
import potato.server.community.service.CommentReadService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@AutoConfigureMockMvc
@SpringBootTest
@ActiveProfiles("test")
class CommentControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CommentReadService commentReadService;

    @Test
    @WithMockCustomMember
    @DisplayName("루트 댓글을 생성한다.")
    void givenCommentCreatRequest_whenCreatingComment_thenReturnCommentId() throws Exception {
        //given
        CommentRequest request = new CommentRequest(null, "코멘트");

        //when & then
        mockMvc.perform(post("/api/v1/posts/1/comments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.uri").exists())
                .andDo(print());
    }

    @Test
    @WithMockCustomMember
    @DisplayName("대댓글을 생성한다.")
    void givenCommentCreatRequestWithParentId_whenCreatingComment_thenReturnCommentId() throws Exception {
        //given
        CommentRequest request = new CommentRequest(1L, "코멘트");

        //when & then
        mockMvc.perform(post("/api/v1/posts/1/comments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.uri").exists())
                .andDo(print());
    }

    @Test
    @DisplayName("게시글 id로 댓글을 조회한다.")
    @WithMockCustomMember
    void givenPostId_whenSearchingComments_thenReturnCommentPageResponse() throws Exception {
        //given

        //when & then
        mockMvc.perform(get("/api/v1/posts/2/comments"))
                .andExpect(status().isOk())
                .andDo(print());
    }

}
