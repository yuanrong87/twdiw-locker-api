package tw.com.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import tw.com.demo.dto.GetGroupInfoRequest;
import tw.com.demo.dto.GetGroupInfoResponse;
import tw.com.demo.entity.SysCodeEntity;
import tw.com.demo.exception.CustomException;
import tw.com.demo.repository.SysCodeRepository;
import tw.com.demo.type.ReturnCodeType;

/**
 * 共用相關 業務邏輯層
 */
@Service
@AllArgsConstructor
@Slf4j
public class CommonService {

    private final SysCodeRepository sysCodeRepository;

    /**
     * 取得系統參數
     * 
     * @param request
     */
    public List<GetGroupInfoResponse> getGroupInfo(GetGroupInfoRequest request) {
        List<SysCodeEntity> sysCodeList = sysCodeRepository.findByGroupName(request.getGroupName());
        if (sysCodeList.isEmpty()) {
            throw new CustomException(ReturnCodeType.GROUP_NAME_NOT_FOUND);
        }

        return sysCodeList.stream().map(sysCode -> {
            GetGroupInfoResponse getGroupInfoResponse = new GetGroupInfoResponse();
            getGroupInfoResponse.setKey(sysCode.getSysKey());
            getGroupInfoResponse.setValue(sysCode.getSysValue());
            return getGroupInfoResponse;
        }).toList();
    }

}
