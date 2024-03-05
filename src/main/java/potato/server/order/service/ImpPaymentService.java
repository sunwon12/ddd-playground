package potato.server.order.service;

import com.siot.IamportRestClient.IamportClient;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import potato.server.common.CustomException;
import potato.server.common.ResultCode;
import potato.server.order.dto.ImpRefundDTO;
import potato.server.order.dto.request.PaymentPreValidateRequest;

import java.math.BigDecimal;
import java.util.Map;

/**
 * @author 정순원
 * @since 23-11-30
 */
@Service
public class ImpPaymentService {

    @Value("${imp.api-key}")
    private String impKey;
    @Value("${imp.api-secret-key}")
    private String impSecretKey;
    private IamportClient iamportClient;


    public ImpPaymentService() {
        this.iamportClient = new IamportClient(impKey, impSecretKey);
    }

    //환불
    public void refund(Long orderId, BigDecimal amount, String reason) {

        String impAccessToken = getImpToken();
        ImpRefundDTO impRefundDTO = new ImpRefundDTO(String.valueOf(orderId), amount, reason);

        JSONObject response = WebClient.create()
                .post()
                .uri("https://api.iamport.kr/payments/cancel")
                .headers(httpHeaders -> httpHeaders.setBearerAuth(impAccessToken))
                .bodyValue(impRefundDTO)
                .retrieve()
                .bodyToMono(JSONObject.class)
                .block();
    }


    //사후 검증
    public BigDecimal paymentInfo(String impUid)  {

        String impAccessToken = getImpToken();

        JSONObject response = WebClient.create()
                .get()
                .uri("https://api.iamport.kr/payments/" + impUid)
                .headers(httpHeaders -> httpHeaders.setBearerAuth(impAccessToken))
                .retrieve()
                .bodyToMono(JSONObject.class)
                .block();
        //TODO 가맹점코드(프론트)로 API 테스트해보고 오류 재정의
        if (response.get("code").toString().equals("-1"))
            throw new CustomException(HttpStatus.BAD_REQUEST, ResultCode.IMP_NOT_ACCESSTOKEN);

        return new BigDecimal(response.get("amount").toString()); //결제금액

    }

    //사전 검증
    public void prepare(PaymentPreValidateRequest request) {

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("merchant_uid", request.getOrderId());
        body.add("amount", request.getAmount());

        String impAccessToken = getImpToken();

        JSONObject response = WebClient.create()
                .post()
                .uri("https://api.iamport.kr/payments/prepare")
                .headers(httpHeaders -> httpHeaders.setBearerAuth(impAccessToken))
                .retrieve()
                .bodyToMono(JSONObject.class)
                .block();
    }

    //아임포트 토큰 가져오기
    private String getImpToken() {
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("imp_key", impKey);
        body.add("imp_secret", impSecretKey);

        JSONObject response = WebClient.create()
                .post()
                .uri("https://api.iamport.kr/users/getToken")
                .body(BodyInserters.fromFormData(body))
                .retrieve()
                .bodyToMono(JSONObject.class)
                .block();

        if (response.get("code").toString().equals("-1"))
            throw new CustomException(HttpStatus.BAD_REQUEST, ResultCode.IMP_NOT_ACCESSTOKEN, (String)response.get("message"));

        Map<String, Object> impResponse = (Map<String, Object>) response.get("response");
        return impResponse.get("access_token").toString();
    }
}