package tw.com.demo.outbound.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApiQrcodeDataRequest {

    @JsonProperty("vcUid")
    private String vcUid;

    @JsonProperty("issuanceDate")
    private String issuanceDate;

    @JsonProperty("expiredDate")
    private String expiredDate;

    @JsonProperty("fields")
    private List<ApiQrcodeDataFieldRequest> fields;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor@Setter
    public static class ApiQrcodeDataFieldRequest {

        @JsonProperty("ename")
        private String ename;

        @JsonProperty("content")
        private String content;

    }

}
