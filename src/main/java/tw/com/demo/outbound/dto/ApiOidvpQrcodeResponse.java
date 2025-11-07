package tw.com.demo.outbound.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiOidvpQrcodeResponse {

    @JsonProperty("transactionId")
    private String transactionId;

    @JsonProperty("qrcodeImage")
    private String qrcodeImage;

    @JsonProperty("authUri")
    private String authUri;

}
