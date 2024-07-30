package potato.server.common;

import lombok.Getter;
import org.springframework.http.HttpStatus;


@Getter
public class CustomException extends RuntimeException {
    private final HttpStatus httpStatus; // HTTP 상태 코드
    private final ResultCode resultCode; // 커스텀 결과 코드
    private final String resultMessage; // 결과 메세지

    public CustomException(HttpStatus httpStatus, ResultCode resultCode) {
        this(httpStatus, resultCode, resultCode.getMessage());
    }

    public CustomException(HttpStatus httpStatus, ResultCode resultCode, String resultMessage) {
        super(resultMessage);
        this.httpStatus = httpStatus;
        this.resultCode = resultCode;
        this.resultMessage = resultMessage;
    }

}

