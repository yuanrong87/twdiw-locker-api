package tw.com.demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "取得智慧取物 QR Code Response")
public class GetLockersQrcodeResponse {

    @JsonProperty("transactionId")
    @Schema(description = "交易序號", example = "716b2006-2d63-4000-912a-86e1b548f0bc")
    private String transactionId;

    @JsonProperty("qrcodeImage")
    @Schema(description = "QR Code", example = "data:image/png;base64,iVBORw0KGgoAAAANS...")
    private String qrcodeImage;

}
