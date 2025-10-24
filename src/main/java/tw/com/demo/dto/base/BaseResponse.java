package tw.com.demo.dto.base;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import tw.com.demo.type.ReturnCodeType;

/**
 * 統一 API 回應格式
 */
@Getter
@Setter
@Schema(description = "response")
public class BaseResponse<T> {

    /**
     * 回應代碼
     */
    private String code;

    /**
     * 回應訊息
     */
    private String message;

    /**
     * 回應資料
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

    public BaseResponse() {
    }

    public BaseResponse(String code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public BaseResponse(String code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * 建立成功回應（有 data）
     */
    public static <T> BaseResponse<T> success(T data) {
        return new BaseResponse<>(ReturnCodeType.SUCCESS.getCode(), ReturnCodeType.SUCCESS.getMessage(), data);
    }

    /**
     * 建立成功回應（無 data）
     */
    public static <T> BaseResponse<T> success() {
        return new BaseResponse<>(ReturnCodeType.SUCCESS.getCode(), ReturnCodeType.SUCCESS.getMessage());
    }

    /**
     * 建立錯誤回應（自訂訊息）
     */
    public static <T> BaseResponse<T> error(String code, String message) {
        return new BaseResponse<>(code, message, null);
    }

    /**
     * 建立錯誤回應（ReturnCodeType 訊息）
     */
    public static <T> BaseResponse<T> error(ReturnCodeType returnCodeType) {
        return new BaseResponse<>(returnCodeType.getCode(), returnCodeType.getMessage(), null);
    }

}
