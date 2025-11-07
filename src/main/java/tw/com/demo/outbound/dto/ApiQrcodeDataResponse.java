package tw.com.demo.outbound.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApiQrcodeDataResponse {

    @JsonProperty("transactionId")
    private String transactionId;

    @JsonProperty("qrCode")
    private String qrCode;

    @JsonProperty("deepLink")
    private String deepLink;

}
