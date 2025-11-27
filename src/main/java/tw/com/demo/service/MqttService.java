package tw.com.demo.service;

import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import tw.com.demo.component.AppConfig;
import tw.com.demo.dto.LockerCommand;

@Service
@RequiredArgsConstructor
@Slf4j
public class MqttService {

    private final AppConfig appConfig;
    
    private final ObjectMapper objectMapper;
    
    private MqttClient client;
    
    @PostConstruct
    public void init() {
        connect();
    }
    
    /**
     * 連線
     */
    private void connect() {
        try {
            String clientId = "SpringLocker_" + System.currentTimeMillis();
            client = new MqttClient(appConfig.getBroker(), clientId, null);
            
            MqttConnectOptions options = new MqttConnectOptions();
            options.setUserName(appConfig.getUsername());
            options.setPassword(appConfig.getPassword().toCharArray());
            options.setSocketFactory(getInsecureSocketFactory());
            options.setCleanSession(true);
            
            client.setCallback(new MqttCallback() {
                
                @Override
                public void messageArrived(String topic, MqttMessage message) throws Exception {
                    String payload = new String(message.getPayload(), StandardCharsets.UTF_8);
                    log.info("收到［{}］訊息：{}", topic, payload);
                }
                
                @Override
                public void deliveryComplete(IMqttDeliveryToken token) {}
                
                @Override
                public void connectionLost(Throwable cause) {
                    log.warn("MQTT 連線遺失：{}", cause.getMessage());
                }
            });
            
            client.connect(options);
            log.info("已連線");
            
            client.subscribe(appConfig.getTopicStatus());
            log.info("已訂閱 topic：{}", appConfig.getTopicStatus());
        } catch (Exception e) {
            log.error("連線失敗", e.getMessage());
        }
    }
    
    /**
     * 開門
     * @param lockerId
     */
    public void open(String lockerId) {
        sendLockerCommand(lockerId, "open");
    }
    
    /**
     * 關門
     * @param lockerId
     */
    public void close(String lockerId) {
        sendLockerCommand(lockerId, "close");
    }
    
    /**
     * 發布指令
     * 
     * @param lockerId
     * @param command
     */
    private void sendLockerCommand(String lockerId, String command) {
        if (client == null || !client.isConnected()) {
            log.info("尚未連線，嘗試重新連線...");
            connect();
        }
        
        try {
            LockerCommand cmd = new LockerCommand();
            cmd.setLockerId(lockerId);
            cmd.setCommand(command);
            
            String json = objectMapper.writeValueAsString(cmd);
            MqttMessage msg = new MqttMessage(json.getBytes(StandardCharsets.UTF_8));
            msg.setQos(1);
            
            client.publish(appConfig.getTopicControl(), msg);
            log.info("發送指令：{}", json);
        } catch (Exception e) {
            log.error("發生錯誤：", e, e);
        }
    }
    
    /**
     * 檢查連線，若掉線自動重新連線
     */
    @Scheduled(fixedDelay = 30000)
    public void ensureConnected() {
        try {
            if (client == null || !client.isConnected()) {
                log.info("檢測到斷線，重新連線中...");
                connect();
            }
        } catch (Exception e) {
            log.error("重連失敗：", e, e);
        }
    }
    
    private SSLSocketFactory getInsecureSocketFactory() throws NoSuchAlgorithmException, KeyManagementException {
        TrustManager[] trustAll = new TrustManager[] {
                new X509TrustManager() {
                    public X509Certificate[] getAcceptedIssuers() { return null; }
                    public void checkClientTrusted(X509Certificate[] certs, String authType) {}
                    public void checkServerTrusted(X509Certificate[] certs, String authType) {}
                }
        };
        SSLContext ctx = SSLContext.getInstance("TLS");
        ctx.init(null, trustAll, new SecureRandom());
        
        return ctx.getSocketFactory();
    }
    
}
