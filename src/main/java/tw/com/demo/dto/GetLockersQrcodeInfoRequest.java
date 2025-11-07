package tw.com.demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "取得驗證資訊 Request")
public class GetLockersQrcodeInfoRequest {

    @NotBlank(message = "交易序號 不可為空")
    @JsonProperty("transactionId")
    @Schema(description = "交易序號", example = "716b2006-2d63-4000-912a-86e1b548f0bc")
    private String transactionId;

}
