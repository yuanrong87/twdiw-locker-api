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
    ERROR_JSON_PARSE("9997", "JSON 格式錯誤"),
    ERROR_CALL_API("9998", "API 呼叫失敗"),
    ERROR_SERVER("9999", "系統發生錯誤");

    @Getter
    private String code;

    @Getter
    private String message;

}
