package tw.com.demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "取得系統參數 Request")
public class GetGroupInfoRequest {

    @NotBlank(message = "群組名稱 不可為空")
    @Schema(description = "群組名稱", example = "LOCATION")
    @JsonProperty("groupName")
    private String groupName;

}
