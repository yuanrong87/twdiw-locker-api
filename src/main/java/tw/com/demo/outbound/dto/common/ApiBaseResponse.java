package tw.com.demo.outbound.dto.common;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import tw.com.demo.type.ApiStatusCode;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiBaseResponse<T> implements Serializable {

    private static final long serialVersionUID = 3937533115408118803L;

    @Setter
    @Getter
    private String code;

    @Setter
    @Getter
    private String msg;

    /** 實際的 ApiBaseResponse */
    @Setter
    @Getter
    T data;

    public ApiBaseResponse() {
        this.setStatusCode(ApiStatusCode.FAIL);
    }

    public ApiBaseResponse(ApiStatusCode statusCode) {
        this.setStatusCode(statusCode);
    }

    public ApiBaseResponse(T data) {
        this.data = data;
        this.setStatusCode(ApiStatusCode.SUCCESS);
    }

    public ApiBaseResponse(T data, ApiStatusCode statusCode) {
        this.data = data;
        this.setStatusCode(statusCode);
    }

    /**
     * 只有 AOP 能用
     *
     * @param msg  msg
     * @param code code
     */
    public ApiBaseResponse(String msg, String code) {
        this.msg = msg;
        this.code = code;
    }

    public void setStatusCode(ApiStatusCode statusCode) {
        if (statusCode != null) {
            this.msg = statusCode.getMsg();
            this.code = statusCode.getCode();
        }
    }
}
