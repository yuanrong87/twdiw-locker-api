# TWDIW Smart Locker API — 專案說明

[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

使用數位憑證皮夾與 MQTT 控制「行動憑證置物櫃」操作（寄物 / 取物 流程） API。

## 目錄

- [專案概述](#專案概述)
- [系統需求](#系統需求)
- [專案結構](#專案結構)
- [主要設定](#主要設定)
- [建置與執行](#建置與執行)
- [API-摘要](#API-摘要)
- [資料庫結構與初始化資料](#資料庫結構與初始化資料)
- [開發與除錯提示](#開發與除錯提示)

---

## 專案概述

這是一個用 Spring Boot (Java 17 + Spring Boot 3.5.6) 實作的智慧置物櫃 API，功能包含：

- 查詢櫃位狀態
- 寄物／配送（建立訂單、更新櫃位狀態、透過 MQTT 開啟櫃門）
- 產生智慧取物 QR Code（整合驗證服務 Verifier）
- 取物（驗證後更新訂單與櫃位狀態並控制 MQTT 開啟櫃門）

本專案採用 H2 in-memory DB（預設）並透過 SQL 腳本初始化表結構與測試資料；同時整合 OpenAPI/Swagger UI 供開發測試用。

## 系統需求

- Java 17
- Maven (用於建置)
- （選用）MQTT broker 供 MQTT 相關功能測試

在 pom.xml 中可看到 Spring Boot 與相關套件（spring-web, spring-data-jpa, spring-security, springdoc-openapi, h2, openfeign, resilience4j, lombok, paho mqtt client）。

## 專案結構

根目錄（重點）:

```
pom.xml
src/
	main/
		java/tw/com/demo/
			TwdiwLockerApiApplication.java        # Spring Boot 啟動類別
			controller/
				LockersController.java             # 櫃子相關 API
				CommonController.java              # 共用/系統參數 API
			service/
				LockersService.java                # 櫃子主要業務邏輯
				VerifierService.java               # (outbound) 驗證服務整合
				MqttService.java                   # MQTT 控制封裝
			repository/
				LockersRepository.java             # JPA repository
				OrderRepository.java
				SysCodeRepository.java
			entity/                              # JPA entity（lockers, orders, sys_code）
			dto/                                 # API request/response DTO
			outbound/                            # 外部 API / Feign DTO 與 adapter
		resources/
			application.yml                      # 預設設定
			schema.sql                            # DB schema 初始化
			data.sql                              # DB seed data
```

（專案中還包含 config、aop、component 等輔助套件與設定類別）

## 主要設定

程式預設使用 `src/main/resources/application.yml`，重要設定節點如下（可被環境變數覆蓋）：

- server.port: 8080
- logging.level.tw.com.demo: INFO
- spring.datasource.url: jdbc:h2:mem:testdb
- spring.datasource.username: sa
- spring.datasource.password: (empty)
- spring.jpa.hibernate.ddl-auto: none
- spring.sql.init.schema-locations: classpath:schema.sql
- spring.sql.init.data-locations: classpath:data.sql
- spring.h2.console.enabled: true
- spring.h2.console.path: /h2-console

Security default user/password 在 `application.yml` 中為：

- spring.security.user.name = ${SPRING_SECURITY_USER_NAME:admin}
- spring.security.user.password = ${SPRING_SECURITY_USER_PASSWORD:1qaz@WSX3edc}

注意：此密碼為預設值（範例），若部署到正式環境請務必覆蓋為更安全的密碼或採用外部認證機制。

MQTT 相關（預設為空，建議在環境變數或外部設定檔中提供）：

- mqtt.broker
- mqtt.username
- mqtt.password
- mqtt.topic-control (預設： locker/control)
- mqtt.topic-status (預設： locker/status)

Verifier（驗證服務）:

- verifier.data.accessToken = ${VERIFIER_DATA_ACCESSTOKEN:} # 注意：預設為空，請在環境中設定
- verifier.data.ref = ${VERIFIER_DATA_REF:} # 注意：預設為空，請在環境中設定
- verifier.server.url.base = ${VERIFIER_SERVER_URL_BASE:https://verifier-sandbox.wallet.gov.tw}

CORS 白名單（application.yml 範例）:

- cors.allowedOrigins: 'http://127.0.0.1:5500, https://127.0.0.1:5500, http://127.0.0.1:8081, https://127.0.0.1:8081, http://localhost:9000'

如何用環境變數覆蓋（macOS zsh 範例）:

```bash
export SPRING_SECURITY_USER_NAME=admin
export SPRING_SECURITY_USER_PASSWORD='yourStrongPassword'
export VERIFIER_DATA_ACCESSTOKEN='your-access-token'
export VERIFIER_DATA_REF='your-ref'
export VERIFIER_SERVER_URL_BASE='https://verifier.example.com'
export MQTT_BROKER='tcp://localhost:1883'
export MQTT_USERNAME='mqtt_user'
export MQTT_PASSWORD='mqtt_pass'

# 然後啟動應用
mvn spring-boot:run
```

注意：Spring Boot 會將環境變數（如 MQTT_BROKER）轉換為對應的屬性名稱（如 `mqtt.broker`）。若使用外部 config（`application-prod.yml` 等），亦可透過 profiles 管理。

## 建置與執行

1. 本地開發 (使用 Maven)：

```bash
# 直接啟動（會使用 application.yml）
mvn spring-boot:run

# 或 build 成 jar 再啟動
mvn clean package -DskipTests
java -jar target/twdiw-locker-api-0.0.1-SNAPSHOT.jar
```

2. 主要位置：

- Swagger UI: http://localhost:8080/swagger-ui/index.html
- H2 Console: http://localhost:8080/h2-console
  - JDBC URL: jdbc:h2:mem:testdb
  - User: sa

## API-摘要

以下為從 controller 檔案掃描得到的主要 API（請參考 src/main/java 下 DTO 以取得 request/response 欄位定義）：

Lockers（路徑 /api/lockers）

- POST /api/lockers/info

  - 取得櫃子狀態
  - Request: GetLockersInfoRequest
  - Response: GetLockersInfoResponse

- POST /api/lockers/operation

  - 寄物/配送（建立訂單、開啟櫃門）
  - Request: HandleLockersOperationRequest
  - Response: BaseResponse (success)

- POST /api/lockers/qrcode

  - 取得智慧取物 QR Code（呼叫 Verifier 產生交易與 QR）
  - Response: GetLockersQrcodeResponse (transactionId, qrcodeImage)

- POST /api/lockers/qrcode/info

  - 取得驗證資訊（查詢 Verifier 交易結果）
  - Request: GetLockersQrcodeInfoRequest
  - Response: GetLockersQrcodeInfoResponse

- POST /api/lockers/pickup
  - 取物（驗證成功後更新訂單 & 櫃位並透過 MQTT 開門）
  - Request: HandleLockersPickupRequest
  - Response: HandleLockersPickupResponse

Common（路徑 /api/common）

- POST /api/common/group/info
  - 取得系統參數（例如 LOCATION, ORDER_STATUS 等）
  - Request: GetGroupInfoRequest
  - Response: List<GetGroupInfoResponse>

註：所有 API 皆包裝於 `BaseResponse<T>`，且 controller 使用了 `@Valid` 進行輸入驗證。

## 資料庫結構與初始化資料

使用 H2 in-memory DB（預設），並由 `src/main/resources/schema.sql` 與 `data.sql` 初始化，主要表格摘要：

- lockers（櫃子檔）

  - id (PK), locker_no, location, is_active, create_time, update_time
  - unique(locker_no, location)

- orders（訂單檔）

  - order_no (PK), send_phone, send_name, receive_phone, receive_name, item, lockers_id, is_pickup, payment, status, create_time, update_time

- sys_code（系統管理檔）
  - id (PK), group_name, sys_key, sys_value, memo, is_active, create_time

初始化資料（data.sql）包含：

- 預設三個 lockers（L0001 ~ L0003）
- sys_code 範例（LOCATION、ORDER_STATUS 等）

如果要改為永久資料庫（例如 Postgres / MySQL），請修改 `spring.datasource.url` 與 driver，並調整 JPA 設定（ddl-auto）與 schema 管理策略。

## 開發與除錯提示

- 查看日誌級別：可透過 `logging.level.tw.com.demo` 調整，或在執行時帶入 `-Dlogging.level.tw.com.demo=DEBUG`。
- 若要本機測試 MQTT 行為，可啟一個本地 broker（例如 mosquitto），並在環境變數中設定 `mqtt.broker`。
- Verifier API 目前指向 sandbox（application.yml 的預設），若要使用正式環境，請設定 `VERIFIER_SERVER_URL_BASE`、`VERIFIER_DATA_ACCESSTOKEN`、`VERIFIER_DATA_REF`。
