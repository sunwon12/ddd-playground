package potato.server.common;

import lombok.Getter;

/**
 * @author 정순원
 * @since 23-10-13
 */
@Getter
public enum ResultCode {

    // 정상 처리
    OK("P000", "요청 정상 처리"),

    // 서버 내부 에러 (5xx 에러)
    INTERNAL_SERVER_ERROR("P100", "서버 에러 발생"),

    // F2xx: JSon 값 예외
    NOT_VALIDATION("P200", "json 값이 올바르지 않습니다."),

    // P3xx: 인증, 권한에 대한 예외
    AUTH_USER_NOT("P300", "현재 권한으로 접근 불가능합니다."),
    JWT_DATE_NOT("P301", "JWT토큰이 만료되었습니다."),
    REFRESHTOKEN_OUTDATED("P302", "새로 발급된 토큰보다 이전의 리프레시 토큰입니다."),
    MAIL_AUTHNUMBER_NOT("P303", "인증번호가 틀립니다."),

    // P4xx: 유저 예외
    USER_NOT_FOUND("P400", "존재하지 않는 유저입니다."),
    USER_ALREADY_JOIN("P401", "이미 회원가입된 유저입니다."),
    USER_NOT_JOINED("P402", "회원가입이 되어있지 않은 유저입니다."),
    USER_NOT_PERMISSION("P403", "권한이 없는 유저입니다."),

    // P5xx 주문예외
    ORDER_NOT_FOUND("P500", "존재하지 않는 주문입니다."),
    ORDER_PRODUCT_NOT_FOUND("P501", "주문 상품이 존재하지 않습니다."),

    // P6xx 상품예외
    PRODUCT_NOT_FOUND("P600", "존재하지 않은 상품입니다."),
    PRODUCT_IMAGE_NOT_FOUND("P601", "존재하지 않는 상품이미지입니다."),

    // P7xx 리뷰예외
    REVIEW_NOT_FOUND("P700", "존재하지 않는 리뷰입니다."),
    REVIEW_NOT_WRITER("P701", "리뷰 작성자가 아닙니다."),
    REVIEW_IMAGE_NOT_FOUND("P702", "존재하지 않는 리뷰 이미지입니다."),

    // P8xx 장바구니 예외
    CART_PRODUCT_NOT_FOUND("P800", "존재하지 않은 장바구니 상품입니다."),
    QUANTITY_LESS_THAN_ONE("P801", "수량은 1 이상이어야 합니다."),
    CART_NOT_FOUND("P802", "유저의 장바구니가 존재하지 않습니다."),

    // P9xx 주소 예외
    ADDRESS_NOT_FOUND("P900", "존재하지 않는 주소입니다."),

    // P10xx 위시리스트 예외
    WISHLIST_NOT_FOUND("P1000", "존재하지 않는 위시리스트입니다."),

    // P11xx 게시글 예외
    POST_NOT_FOUND("P1100", "존재하지 않는 게시글입니다."),

    // P12xx 댓글 예외
    COMMENT_NOT_FOUNT("P1200", "존재하지 않는 댓글입니다."),
    PARENT_COMMENT_POST_MISS_MATCH("P1201", "부모 댓글의 게시글과 댓글을 작성할 게시글이 다릅니다."),
      
    //P11xx 결제 예외
    IMP_PREVALIDATION_FAIL("P1200", "결제정보 사전등록에 실패하였습니다(이미 등록된 merchant_uid입니다)."),
    IMP_NOT_VALIDAION("P1201", "잘못된 API키,secret키입니다."),
    IMP_NOT_ACCESSTOKEN("P1202", "유효하지 않은 아임포트 엑세스토큰입니다."),
    IMP_NOT_AMOUNT("P1203", "결제예상액과 결제금액이 다릅니다.")
    ;

    private final String code;
    private final String message;


    ResultCode(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
