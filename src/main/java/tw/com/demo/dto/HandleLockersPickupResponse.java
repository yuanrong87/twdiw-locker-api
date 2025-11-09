package tw.com.demo.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "取物 Response")
public class HandleLockersPickupResponse {

    @Schema(description = "置物櫃編號", example = "L0001")
    @JsonProperty("lockerNo")
    private List<String> lockerNo;

}
