package tw.com.demo.outbound.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiOidvpResultRequest {

    @JsonProperty("transactionId")
    @Schema(description = "交易序號", example = "716b2006-2d63-4000-912a-86e1b548f0bc")
    private String transactionId;

}
