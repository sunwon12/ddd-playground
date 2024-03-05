package potato.server.common;

import lombok.Getter;

/**
 * 요청 결과의 상태를 저장하는 클래스
 * @author 정순원
 * @since 2023-10-13
 */
@Getter
public class StatusResponse {
    private final String resultCode;
    private final String resultMessage;

    public StatusResponse(ResultCode resultCode) {
        this(resultCode.getCode(), resultCode.getMessage());
    }

    public StatusResponse(String resultCode, String resultMessage) {
        this.resultCode = resultCode;
        this.resultMessage = resultMessage;
    }
}
