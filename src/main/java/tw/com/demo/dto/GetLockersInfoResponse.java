package tw.com.demo.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "取得櫃子狀態 Response")
public class GetLockersInfoResponse {

    @JsonProperty("totalLockers")
    @Schema(description = "櫃子總數量", example = "2")
    private Integer totalLockers;

    @JsonProperty("lockerInfo")
    @Schema(description = "櫃子資訊")
    private List<LockerInfoDto> lockerInfo;


    @Getter
    @Setter
    public static class LockerInfoDto {

        @Schema(description = "置物櫃編號", example = "L0001")
        @JsonProperty("lockerNo")
        private String lockerNo;

        @Schema(description = "是否可使用", example = "true")
        @JsonProperty("isActive")
        private boolean isActive;

    }

}
