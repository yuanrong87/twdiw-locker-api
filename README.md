// ...existing code...

# TWDIW Smart Locker API — 精簡說明

摘要：

- Spring Boot 17 + OpenFeign + H2 (in-memory)
- 功能：查詢櫃位、寄物/配送（建立訂單 + MQTT 開門）、產生/查詢智慧取物 QR Code、取物
- API 包裝為統一格式 BaseResponse<T>

快速上手：

1. 設定（可用環境變數或改 application.yml）：
   - MQTT: `mqtt.broker`, `mqtt.username`, `mqtt.password`
   - 驗證端沙盒系統相關設定: `verifier.server.url.base`, `verifier.data.accessToken`, `verifier.data.ref`
2. 本機啟動：
   mvn spring-boot:run
3. 主要位置：
   - Swagger: /swagger-ui/index.html
   - H2 Console: /h2-console (JDBC: jdbc:h2:mem:testdb, user: sa)

簡要 API（路徑 /api/lockers /api/common）：

- POST /api/lockers/info
- POST /api/lockers/operation
- POST /api/lockers/qrcode
- POST /api/lockers/qrcode/info
- POST /api/lockers/pickup
- POST /api/common/group/info

編譯與執行

- 需求：Java 17、Maven
- 編譯（含 package，不執行測試）：
  mvn -DskipTests package
- 編譯並執行測試：
  mvn test
- 本機啟動（開發）：
  mvn spring-boot:run
- 產生 jar 後執行：
  mvn package
  java -jar target/twdiw-locker-api-0.0.1-SNAPSHOT.jar
