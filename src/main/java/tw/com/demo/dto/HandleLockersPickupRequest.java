package tw.com.demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "取物 Request")
public class HandleLockersPickupRequest {

    @NotBlank(message = "據點 不可為空")
    @Schema(description = "據點", example = "1")
    @JsonProperty("location")
    private String location;

    @NotNull(message = "已付金額 不可為空")
    @Schema(description = "已付金額", example = "50")
    @JsonProperty("payment")
    private Integer payment;

    @NotBlank(message = "寄件人姓名 不可為空")
    @Schema(description = "寄件人姓名", example = "林大明")
    @JsonProperty("receiveName")
    private String receiveName;

    @NotBlank(message = "寄件人手機號碼 不可為空")
    @Schema(description = "寄件人手機號碼", example = "09XXX")
    @JsonProperty("receivePhone")
    private String receivePhone;

}
