package tw.com.demo.exception;

import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;

import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import tw.com.demo.dto.base.BaseResponse;
import tw.com.demo.type.ReturnCodeType;

/**
 * Handle 錯誤及處理
 */
@Slf4j
@ControllerAdvice
public class CustomExceptionHandler {

    /**
     * 處理呼叫其它 API 的例外
     * 
     * @param <T>
     * @param ex
     * @return
     */
    @ExceptionHandler(FeignException.class)
    public <T> ResponseEntity<BaseResponse<T>> handleFeignException(FeignException ex) {
        log.error("handleFeignException : {}", ex);
        BaseResponse<T> response = BaseResponse.error(ReturnCodeType.ERROR_CALL_API);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * 處理自訂義的例外
     * 
     * @param <T>
     * @param ex
     * @return
     */
    @ExceptionHandler(CustomException.class)
    public <T> ResponseEntity<BaseResponse<T>> handleDWException(CustomException ex) {
        log.warn("handleDWException : {}", ex);
        BaseResponse<T> response = BaseResponse.error(ex.getCode(), ex.getDesc());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * 處理驗證失敗的例外
     * 
     * @param <T>
     * @param ex
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public <T> ResponseEntity<BaseResponse<T>> handleValidationException(MethodArgumentNotValidException ex) {
        log.warn("handleValidationException : {}", ex);
        String errorMsg = ex.getBindingResult().getFieldErrors().stream().map(it -> it.getDefaultMessage())
                .collect(Collectors.joining(", "));
        BaseResponse<T> response = BaseResponse.error(ReturnCodeType.ERROR_VALID.getCode(),
                ReturnCodeType.ERROR_VALID.getMessage() + errorMsg);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * 處理 JSON 解析錯誤
     * 
     * @param <T>
     * @param ex
     * @return
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public <T> ResponseEntity<BaseResponse<T>> handleHttpMessageNotReadableException(
            HttpMessageNotReadableException ex) {
        log.warn("handleHttpMessageNotReadableException : {}", ex);
        String errorMsg = "";
        // 檢查是否是未知屬性錯誤
        if (ex.getCause() instanceof UnrecognizedPropertyException unrecognizedPropertyException) {
            String propertyName = unrecognizedPropertyException.getPropertyName();
            errorMsg = "請求包含未知屬性: " + propertyName;
        } else {
            // 其他 JSON 解析錯誤
            errorMsg = "請求格式錯誤: " + ex.getMessage();
        }

        BaseResponse<T> response = BaseResponse.error(ReturnCodeType.ERROR_VALID.getCode(), errorMsg);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * 處理其他非預期的例外
     * 
     * @param <T>
     * @param ex
     * @return
     */
    @ExceptionHandler(Exception.class)
    public <T> ResponseEntity<BaseResponse<T>> handleException(Exception ex) {
        log.error("handleException : {}", ex);
        BaseResponse<T> response = BaseResponse.error(ReturnCodeType.ERROR_SERVER);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
