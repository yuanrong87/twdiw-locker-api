package tw.com.demo.exception;

import lombok.Getter;
import tw.com.demo.type.ReturnCodeType;

/**
 * 客製錯誤
 */
@Getter
public class CustomException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private final String code;

    private final String desc;

    public CustomException() {
        this(ReturnCodeType.ERROR_SERVER);
    }

    public CustomException(String code, String desc) {
        super(desc);
        this.code = code;
        this.desc = desc;
    }

    public CustomException(ReturnCodeType returnCodeType) {
        this(returnCodeType.getCode(), returnCodeType.getMessage());
    }

}
