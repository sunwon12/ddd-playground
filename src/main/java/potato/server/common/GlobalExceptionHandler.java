package potato.server.common;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 컨트롤러에서 발생하는 전역적인 예외를 캐치해서 핸들링
 *
 * @author 정순원
 * @since 2023-10-13
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 예기치 못한 서버 내부의 모든 에러를 핸들링
     *
     * @param e       Java 최상위 예외 클래스
     * @param request 요청 서블릿 객체
     * @return internal server error 에 대한 에러 응답 json
     */
    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ResponseForm<Void>> handleInternalErrorException(Exception e, HttpServletRequest request) {
        log.error("[서버 에러] from {} api", request.getRequestURI(), e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new ResponseForm<>(ResultCode.INTERNAL_SERVER_ERROR)
        );
    }

    /**
     * 클라이언트 에러를 커스터마이징하여 핸들링
     *
     * @param e       커스텀 서비스 최상단 예외 클래스
     * @param request 요청 서블릿 객체
     * @return 커스텀 에러 코드의 내용에 따른 에러 응답 json
     */
    @ExceptionHandler(CustomException.class)
    protected ResponseEntity<ResponseForm<Void>> handleCustomClientErrorException(CustomException e, HttpServletRequest request) {
        log.warn("[클라이언트 에러] from {} api", request.getRequestURI(), e);
        return ResponseEntity.status(e.getHttpStatus()).body(
                new ResponseForm<>(e.getResultCode())
        );
    }

    /**
     * 클라이언트가 타당하지 않은 값을 넘겨주었을 생기는 에러를 커스터마이징하여 핸들링
     *
     * @param e       메소드파라미터Valid 예외 클래스
     * @param request 요청 서블릿 객체
     * @return 커스텀 에러 코드의 내용에 따른 에러 응답 json
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ResponseForm<Void>> hadleMethodArgumentNotValidExecption(MethodArgumentNotValidException e, HttpServletRequest request) {
        log.error("[클라이언트 에러] from {} api", request.getRequestURI(), e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ResponseForm<>(ResultCode.NOT_VALIDATION)
        );
    }

    /**
     * 클라이언트가 인가되지 않은 사용사로 요청 보냈을 때 생기는 에러를 커스터마이징하여 핸들링 (상태 코드 4xx 응답)
     *
     * @param e       인가 에러 AccessDeniedException 예외 클래스
     * @param request 요청 서블릿 객체
     * @return 커스텀 에러 코드의 내용에 따른 에러 응답 json
     */
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ResponseForm<Void>> handleAccessDeniedException(AccessDeniedException e, HttpServletRequest request) {
        log.error("[클라이언트 에러] from {} api", request.getRequestURI(), e);
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(
                new ResponseForm<>(ResultCode.AUTH_USER_NOT)
        );
    }

}

