package potato.server.address;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import potato.server.common.WithMockCustomMember;
import potato.server.user.domain.Address;
import potato.server.user.domain.User;
import potato.server.user.dto.request.CreateAddressRequest;
import potato.server.user.dto.request.UpdateAddressRequest;
import potato.server.user.repository.AddressRepository;
import potato.server.user.repository.UserRepository;
import potato.server.user.spec.DefaultAddr;
import potato.server.user.spec.Gender;
import potato.server.user.spec.JoinType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author 정순원
 * @since 2024-01-16
 */
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class AddressAPITest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    AddressRepository addressRepository;
    @Autowired
    UserRepository userRepository;
    Address address1;
    Address address2;
    Address address3;
    User user;
    @Autowired
    ObjectMapper objectMapper;

    @BeforeEach
    void init()  {
        user = User.builder()
                .name("sun won")
                .email("test@test.com")
                .providerId("kakao_test")
                .joinType(JoinType.KAKAO)
                .gender(Gender.MALE)
                .providerName("kakao")
                .phoneNumber("123-1234-1234")
                .build();

        userRepository.save(user);

        address1 = Address.builder()
                .user(user)
                .defaultAddr(DefaultAddr.Y)
                .addr("soongsil")
                .addrDetail("302호")
                .addZipcode("1234")
                .addrRequest("문 앞에 놔두세요")
                .recipient("집주인")
                .userPhone("123-1234-1234")
                .build();

        address2 = Address.builder()
                .user(user)
                .defaultAddr(DefaultAddr.N)
                .addr("soongsil")
                .addrDetail("302호")
                .addZipcode("1234")
                .addrRequest("문 앞에 놔두세요")
                .recipient("집주인")
                .userPhone("123-1234-1234")
                .build();

        address3 = Address.builder()
                .user(user)
                .defaultAddr(DefaultAddr.N)
                .addr("soongsil")
                .addrDetail("302호")
                .addZipcode("1234")
                .addrRequest("문 앞에 놔두세요")
                .recipient("집주인")
                .userPhone("123-1234-1234")
                .build();

        addressRepository.save(address1);
        addressRepository.save(address2);
        addressRepository.save(address3);
    }

    @DisplayName("주소 생성")
    @Test
    @Transactional
    @WithMockCustomMember
    void test1() throws Exception {

        CreateAddressRequest request = new CreateAddressRequest();
        request.setRecipient("sun won");
        request.setAddZipcode("12345");
        request.setAddr("1234 Main St");
        request.setAddrDetail("Apartment 101");
        request.setAddrName("Home");
        request.setUserPhone("123-1234-7890");

        String jsonRequest = objectMapper.writeValueAsString(request);

        mockMvc.perform(post("/api/v1/addresses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @DisplayName("주소 삭제")
    @Test
    @Transactional
    @WithMockCustomMember
    void test2() throws Exception {

        mockMvc.perform(delete("/api/v1/addresses/" + address1.getId()))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @DisplayName("주소 수정")
    @Test
    @Transactional
    @WithMockCustomMember
    void test3() throws Exception {

        UpdateAddressRequest request = new UpdateAddressRequest();

        request.setAddressId(address2.getId());
        request.setAddrName("company");
        request.setUserPhone("123-7777-7777");
        request.setDefaultAddr(DefaultAddr.Y);

        String jsonRequest = objectMapper.writeValueAsString(request);

        mockMvc.perform(put("/api/v1/addresses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk())
                .andDo(print());
    }


    @DisplayName("특정 유저의 모든 주소 조회")
    @Test
    @Transactional
    @WithMockCustomMember
    void test4() throws Exception {

        mockMvc.perform(get("/api/v1/addresses/all"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @DisplayName("특정 유저의 기본 주소지 조회")
    @Test
    @Transactional
    @WithMockCustomMember
    void test5() throws Exception {

        mockMvc.perform(get("/api/v1/addresses/default"))
                .andExpect(status().isOk())
                .andDo(print());
    }
}
