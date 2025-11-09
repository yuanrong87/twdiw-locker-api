package tw.com.demo.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 訊息代碼
 */
@AllArgsConstructor
public enum ReturnCodeType {
    SUCCESS("0000", "SUCCESS"),
    ERROR_VALID("0001", "欄位檢核錯誤: "),
    GROUP_NAME_NOT_FOUND("9989", "查無系統參數"),
    ORDER_NOT_FOUND("9990", "查無訂單"),
    ERROR_GET_VP_RESULT("9991", "查詢 VP 展示驗證結果"),
    LOCKER_IS_NOT_ACTIVE("9992", "置物櫃使用中"),
    LOCKER_NOT_FOUND("9993", "查無置物櫃"),
    ERROR_GET_ITEM("9994", "操錯項目錯誤"),
    GET_LOCKERS_INFO_NOT_FOUND("9995", "查無櫃子狀態"),
    ERROR_GET_VP_QR_CODE("9996", "產生授權 QR Code 失敗"),
    ERROR_JSON_PARSE("9997", "JSON 格式錯誤"),
    ERROR_CALL_API("9998", "API 呼叫失敗"),
    ERROR_SERVER("9999", "系統發生錯誤");

    @Getter
    private String code;

    @Getter
    private String message;

}
