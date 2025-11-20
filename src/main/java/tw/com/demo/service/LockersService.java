package tw.com.demo.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import tw.com.demo.component.AppConfig;
import tw.com.demo.dto.GetLockersInfoRequest;
import tw.com.demo.dto.GetLockersInfoResponse;
import tw.com.demo.dto.GetLockersInfoResponse.LockerInfoDto;
import tw.com.demo.dto.GetLockersQrcodeInfoRequest;
import tw.com.demo.dto.GetLockersQrcodeInfoResponse;
import tw.com.demo.dto.GetLockersQrcodeInfoResponse.VerifyResultDataClaimsDto;
import tw.com.demo.dto.GetLockersQrcodeInfoResponse.VerifyResultDataDto;
import tw.com.demo.dto.GetLockersQrcodeResponse;
import tw.com.demo.dto.HandleLockersOperationRequest;
import tw.com.demo.dto.HandleLockersPickupRequest;
import tw.com.demo.dto.HandleLockersPickupResponse;
import tw.com.demo.entity.LockersEntity;
import tw.com.demo.entity.OrdersEntity;
import tw.com.demo.exception.CustomException;
import tw.com.demo.outbound.dto.ApiOidvpQrcodeResponse;
import tw.com.demo.outbound.dto.ApiOidvpResultResponse;
import tw.com.demo.repository.LockersRepository;
import tw.com.demo.repository.OrderRepository;
import tw.com.demo.type.ItemType;
import tw.com.demo.type.OrderStatus;
import tw.com.demo.type.ReturnCodeType;

/**
 * 櫃子相關 業務邏輯層
 */
@Service
@AllArgsConstructor
@Slf4j
public class LockersService {

    private final AppConfig appConfig;

    private final LockersRepository lockersRepository;

    private final OrderRepository orderRepository;

    private final VerifierService verifierService;

    private final MqttService mqttService;

    /**
     * 取得櫃子狀態
     * 
     * @param request
     */
    public GetLockersInfoResponse getLockersInfo(GetLockersInfoRequest request) {
        List<LockersEntity> lockers = lockersRepository.findByLocation(request.getLocation());
        if (lockers.isEmpty()) {
            throw new CustomException(ReturnCodeType.GET_LOCKERS_INFO_NOT_FOUND);
        }

        List<LockerInfoDto> lockerInfo = lockers.stream().map(locker -> {
            LockerInfoDto lockerInfoDto = new LockerInfoDto();
            lockerInfoDto.setLockerNo(locker.getLockerNo());
            lockerInfoDto.setActive(locker.getIsActive());
            return lockerInfoDto;
        }).toList();

        GetLockersInfoResponse response = new GetLockersInfoResponse();
        response.setTotalLockers(lockers.size());
        response.setLockerInfo(lockerInfo);

        return response;
    }

    /**
     * 寄物/配送
     * 
     * @param request
     */
    @Transactional
    public void handleLockersOperation(HandleLockersOperationRequest request) {
        ItemType itemType = ItemType.toItemType(request.getItem());
        // 檢核 item 是否正確
        if (null == itemType) {
            throw new CustomException(ReturnCodeType.ERROR_GET_ITEM);
        }

        // 查詢 置物櫃
        Optional<LockersEntity> lockersOpt = lockersRepository.findByLocationAndLockerNo(request.getLocation(),
                request.getLockerNo());
        // 檢核 置物櫃是否存在
        if (lockersOpt.isEmpty()) {
            throw new CustomException(ReturnCodeType.LOCKER_NOT_FOUND);
        }
        LockersEntity lockers = lockersOpt.get();

        // 檢核 置物櫃是否可使用
        if (Boolean.TRUE.equals(lockers.getIsActive())) {
            throw new CustomException(ReturnCodeType.LOCKER_IS_NOT_ACTIVE);
        }

        // 修改 置物櫃為已使用
        lockers.setIsActive(true);
        lockers.setUpdateTime(LocalDateTime.now());
        LockersEntity lockersResult = lockersRepository.save(lockers);

        // 建立訂單
        OrdersEntity ordersEntity = new OrdersEntity();
        ordersEntity.setOrderNo(this.getOrserNo());
        ordersEntity.setSendPhone(request.getSendPhone());
        ordersEntity.setSendName(request.getSendName());
        ordersEntity.setReceivePhone(request.getReceivePhone());
        ordersEntity.setReceiveName(request.getReceiveName());
        ordersEntity.setItem(request.getItem());
        ordersEntity.setLockersId(lockersResult.getId());
        ordersEntity.setIsPickup(false);
        ordersEntity.setPayment(request.getPayment());
        ordersEntity.setStatus(itemType.getCode());
        orderRepository.save(ordersEntity);

        // 開置物櫃
        mqttService.open(lockersResult.getLockerNo());
    }

    /**
     * 取得智慧取物 QR Code
     * 
     * @return
     */
    public GetLockersQrcodeResponse getLockersQrcode() {
        // 交易序號
        String transactionId = UUID.randomUUID().toString();

        // 呼叫 API 取得智慧取物 QR Code
        ApiOidvpQrcodeResponse apiResult = verifierService.getVpQrcode(appConfig.getVerifierDataRef(), transactionId);

        // 回傳結果
        GetLockersQrcodeResponse response = new GetLockersQrcodeResponse();
        response.setTransactionId(apiResult.getTransactionId());
        response.setQrcodeImage(apiResult.getQrcodeImage());

        return response;
    }

    /**
     * 取得驗證資訊
     * 
     * @param request
     * @return
     */
    public GetLockersQrcodeInfoResponse getLockersQrcodeInfo(GetLockersQrcodeInfoRequest request) {
        // 交易序號
        String transactionId = request.getTransactionId();

        // 呼叫 API 查詢 VP 展示驗證結果
        ApiOidvpResultResponse apiResult = verifierService.getVpResult(transactionId);

        List<VerifyResultDataDto> dataList = apiResult.getData().stream().map(data -> {
            VerifyResultDataDto dto = new VerifyResultDataDto();
            dto.setCredentialType(data.getCredentialType());

            List<VerifyResultDataClaimsDto> claims = data.getClaims().stream().map(claim -> {
                VerifyResultDataClaimsDto claimDto = new VerifyResultDataClaimsDto();
                claimDto.setEname(claim.getEname());
                claimDto.setCname(claim.getCname());
                claimDto.setValue(claim.getValue());
                return claimDto;
            }).toList();

            dto.setClaims(claims);
            return dto;
        }).toList();

        // 回傳結果
        GetLockersQrcodeInfoResponse response = new GetLockersQrcodeInfoResponse();
        response.setTransactionId(apiResult.getTransactionId());
        response.setData(dataList);

        return response;
    }

    /**
     * 取物
     * 
     * @param request
     * @return
     */
    @Transactional
    public HandleLockersPickupResponse handleLockersPickup(HandleLockersPickupRequest request) {
        LocalDateTime now = LocalDateTime.now();

        // 查詢 置物櫃
        List<LockersEntity> lockers = lockersRepository.findByLocationAndIsActiveTrue(request.getLocation());
        if (lockers.isEmpty()) {
            throw new CustomException(ReturnCodeType.LOCKER_NOT_FOUND);
        }

        // 查詢 訂單
        List<Long> lockerIds = lockers.stream().map(LockersEntity::getId).toList();
        List<String> statusList = Arrays.asList(OrderStatus.DEPOSIT.getCode(), OrderStatus.ARRIVED.getCode());
        List<OrdersEntity> orders = orderRepository.findByReceivePhoneAndReceiveNameAndStatusInAndLockersIdIn(
                request.getReceivePhone(), request.getReceiveName(), statusList, lockerIds);
        if (orders.isEmpty()) {
            throw new CustomException(ReturnCodeType.ORDER_NOT_FOUND);
        }

        // 更新 訂單狀態
        orders.forEach(order -> {
            order.setPayment(request.getPayment());
            order.setStatus(OrderStatus.FINISH.getCode());
            order.setUpdateTime(now);
        });
        orderRepository.saveAll(orders);

        // 查詢 取物置物櫃
        List<Long> pickupLockerIds = orders.stream()
                .map(OrdersEntity::getLockersId)
                .distinct() // 避免重複更新相同 locker
                .toList();

        // 更新 置物櫃狀態
        List<LockersEntity> pickupLockers = lockersRepository.findByIdIn(pickupLockerIds);
        pickupLockers.forEach(locker -> {
            locker.setIsActive(false);
            locker.setUpdateTime(now);
        });
        lockersRepository.saveAll(pickupLockers);

        List<String> lockerNos = pickupLockers.stream()
                .map(LockersEntity::getLockerNo)
                .toList();

        // 開置物櫃
        lockerNos.forEach(mqttService::open);

        // 回傳結果
        HandleLockersPickupResponse response = new HandleLockersPickupResponse();
        response.setLockerNo(lockerNos);

        return response;
    }

    private String getOrserNo() {
        // OR + 當天日期YYYYMMDDHHmmss + 四碼隨機碼
        String randomCode = RandomStringUtils.secure().nextNumeric(4);
        return "OR" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")) + randomCode;
    }
}
