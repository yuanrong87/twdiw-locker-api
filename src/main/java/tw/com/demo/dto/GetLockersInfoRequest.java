package tw.com.demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "取得櫃子狀態 Request")
public class GetLockersInfoRequest {

    @NotBlank(message = "據點 不可為空")
    @Schema(description = "據點", example = "1")
    @JsonProperty("location")
    private String location;

}
