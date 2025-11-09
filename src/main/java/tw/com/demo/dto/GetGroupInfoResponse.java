package tw.com.demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "取得系統參數 Response")
public class GetGroupInfoResponse {

    @Schema(description = "群組名稱", example = "1")
    @JsonProperty("key")
    private String key;

    @Schema(description = "群組名稱", example = "台北車站")
    @JsonProperty("value")
    private String value;

}
