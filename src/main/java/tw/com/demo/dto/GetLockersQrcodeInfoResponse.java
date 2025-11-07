package tw.com.demo.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "取得驗證資訊 Response")
public class GetLockersQrcodeInfoResponse {

    @JsonProperty("transactionId")
    @Schema(description = "交易序號", example = "716b2006-2d63-4000-912a-86e1b548f0bc")
    private String transactionId;

    @JsonProperty("data")
    @Schema(description = "使用者上傳的數位憑證資料，包含憑證類型及相關聲明資訊")
    private List<VerifyResultDataDto> data;

    @Getter
    @Setter
    public static class VerifyResultDataDto {

        @Schema(description = "數位憑證的類型識別碼，用於識別不同種類的憑證", example = "VirtualCardCredential")
        @JsonProperty("credentialType")
        private String credentialType;

        @Schema(description = "憑證中包含的聲明資料陣列，每個聲明包含英文名稱、中文名稱和值")
        @JsonProperty("claims")
        private List<VerifyResultDataClaimsDto> claims;

    }

    @Getter
    @Setter
    public static class VerifyResultDataClaimsDto {

        @Schema(description = "英文欄位名稱", example = "name")
        @JsonProperty("ename")
        private String ename;

        @Schema(description = "中文顯示名稱", example = "姓名")
        @JsonProperty("cname")
        private String cname;

        @Schema(description = "實際內容值", example = "黃ＯＯ")
        @JsonProperty("value")
        private String value;

    }

}
