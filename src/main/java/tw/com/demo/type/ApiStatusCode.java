package tw.com.demo.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum ApiStatusCode {
    FAIL("-1", "FAIL"),
    SUCCESS("0", "SUCCESS");

    @Getter
    private final String code;

    @Getter
    private final String msg;

    public static ApiStatusCode toStatusCode(String code) {
        for (ApiStatusCode tmp : ApiStatusCode.values()) {
            if (tmp.getCode().equals(code)) {
                return tmp;
            }
        }
        return null;
    }
}
