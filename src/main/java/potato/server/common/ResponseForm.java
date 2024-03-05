package potato.server.common;

import lombok.Data;

/**
 * 응답 바디 공통 DTO 양식
 * 요청에 정상적으로 성공하면 data 사용, 에러가 발생하면 StatusResponse 핸들링
 *
 * @param <T> Response Body 의 dto 클래스 타입
 * @author 정순원
 * @since 2023-10-13
 */
@Data
public class ResponseForm<T> {

    private final T data;
    private StatusResponse statusResponse = new StatusResponse(ResultCode.OK); // 디폴트로 성공 처리

    public ResponseForm() {
        this.data = null;
    }

    /*
        요청 성공 시, 응답 dto 객체를 파라미터로 받음
     */
    public ResponseForm(T data) {
        this.data = data;
    }

    /*
        요청 실패에 따른 생성자 처리
     */
    public ResponseForm(ResultCode resultCode) {
        this();
        this.statusResponse = new StatusResponse(resultCode);
    }

    public ResponseForm(String resultCode, String resultMessage) {
        this();
        this.statusResponse = new StatusResponse(resultCode, resultMessage);
    }
}

