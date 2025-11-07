package tw.com.demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "寄物/配送 Request")
public class HandleLockersOperationRequest {

    @NotBlank(message = "item 不可為空")
    @Schema(description = "1 寄物、2 配送", example = "1")
    @JsonProperty("item")
    private String item;

    @NotBlank(message = "據點 不可為空")
    @Schema(description = "據點", example = "1")
    @JsonProperty("location")
    private String location;

    @NotBlank(message = "置物櫃編號 不可為空")
    @Schema(description = "置物櫃編號", example = "L0001")
    @JsonProperty("lockerNo")
    private String lockerNo;

    @NotBlank(message = "寄件人姓名 不可為空")
    @Schema(description = "寄件人姓名", example = "王小明")
    @JsonProperty("sendName")
    private String sendName;

    @NotBlank(message = "寄件人手機號碼 不可為空")
    @Schema(description = "寄件人手機號碼", example = "09XXX")
    @JsonProperty("sendPhone")
    private String sendPhone;

    @NotBlank(message = "寄件人姓名 不可為空")
    @Schema(description = "寄件人姓名", example = "林大明")
    @JsonProperty("receiveName")
    private String receiveName;

    @NotBlank(message = "寄件人手機號碼 不可為空")
    @Schema(description = "寄件人手機號碼", example = "09XXX")
    @JsonProperty("receivePhone")
    private String receivePhone;

    @NotNull(message = "已付金額 不可為空")
    @Schema(description = "已付金額", example = "50")
    @JsonProperty("payment")
    private Integer payment;

}
