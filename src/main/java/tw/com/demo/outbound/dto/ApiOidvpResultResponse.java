package tw.com.demo.outbound.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiOidvpResultResponse {

    @JsonProperty("verifyResult")
    @Schema(description = "表示本次驗證的結果狀態", example = "true")
    private boolean verifyResult;

    @JsonProperty("resultDescription")
    @Schema(description = "提供驗證結果的詳細說明文字，success：驗證成功、failed：驗證失敗、expired：憑證已過期、invalid：憑證無效", example = "success")
    private String resultDescription;

    @JsonProperty("transactionId")
    @Schema(description = "本次驗證結果對應的交易序號，與呼叫 API 時使用的 transactionId 相同", example = "716b2006-2d63-4000-912a-86e1b548f0bc")
    private String transactionId;

    @JsonProperty("data")
    @Schema(description = "使用者上傳的數位憑證資料，包含憑證類型及相關聲明資訊")
    private List<ApiVerifyResultDataDto> data;

    @Getter
    @Setter
    public static class ApiVerifyResultDataDto {

        @Schema(description = "數位憑證的類型識別碼，用於識別不同種類的憑證", example = "VirtualCardCredential")
        @JsonProperty("credentialType")
        private String credentialType;

        @Schema(description = "憑證中包含的聲明資料陣列，每個聲明包含英文名稱、中文名稱和值")
        @JsonProperty("claims")
        private List<ApiVerifyResultDataClaimsDto> claims;

    }

    @Getter
    @Setter
    public static class ApiVerifyResultDataClaimsDto {

        @Schema(description = "聲明的英文欄位名稱，用於程式處理和識別", example = "name")
        @JsonProperty("ename")
        private String ename;

        @Schema(description = "聲明的中文顯示名稱，用於使用者介面顯示", example = "姓名")
        @JsonProperty("cname")
        private String cname;

        @Schema(description = "聲明的實際內容值，為使用者提供的資料", example = "黃ＯＯ")
        @JsonProperty("value")
        private String value;

    }

}
