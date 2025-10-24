package tw.com.demo.aop.logging;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import tw.com.demo.dto.base.BaseResponse;

@Slf4j
@Aspect
@Component
public class LoggerAop {

    private ThreadLocal<Long> startTime = new ThreadLocal<>();

    private static final String LINE = "--------------------------------";

    private final HttpServletRequest request;

    private final ObjectMapper mapper;

    public LoggerAop(HttpServletRequest request, ObjectMapper mapper) {
        this.request = request;
        this.mapper = mapper;
    }

    /**
     * 切割點
     */
    @Pointcut("execution(public * tw.com.demo.controller..*.*(..))")
    public void loggerAopPointCut() {
    }

    /**
     * Controller 前
     * 
     * @param point
     * @throws JsonProcessingException
     * @throws Throwable
     */
    @Before("loggerAopPointCut()")
    public void beforeLog(JoinPoint point) throws JsonProcessingException {
        // 【起始時間】
        startTime.set(System.currentTimeMillis());
        // 【LOG】記錄
        log.info(LINE);
        log.info("【請求訊息】");
        log.info(LINE);
        log.info("【URL】{} ", request.getRequestURL());
        log.info("【IP】{}", getIp());
        log.info("【METHOD】{}", request.getMethod());
        log.info("【CONTROLLER】{}", point.getSignature().getDeclaringTypeName());
        log.info("【CONTROLLER METHOD】{}", point.getSignature().getName());
        // 【METHOD】區分 GET 跟 非GET 記錄
        if (!"GET".equalsIgnoreCase(request.getMethod())) {
            Object[] bodys = point.getArgs();
            for (Object body : bodys) {
                log.info("【BODY】{}", mapper.writeValueAsString(body));
            }
        }
        log.info(LINE);
    }

    /**
     * Controller 後
     * 
     * @param data
     * @throws JsonProcessingException
     */
    @AfterReturning(pointcut = "loggerAopPointCut()", returning = "data")
    public void afterLog(Object data) throws JsonProcessingException {
        // 【LOG】記錄
        log.info(LINE);
        log.info("【回傳訊息】");
        log.info(LINE);
        // 【LOG】時間紀錄
        log.info("【TIME】{} 毫秒", System.currentTimeMillis() - startTime.get());
        // 【LOG】記錄回傳資料
        if (data != null && data instanceof BaseResponse) {
            @SuppressWarnings("unchecked")
            BaseResponse<Object> body = (BaseResponse<Object>) data;
            log.info("【RETURN_CODE】{}", body.getCode());
            log.info("【RETURN_DESC】{}", body.getMessage());
            // 【DATA】RESULT 判斷是否紀錄
            if (body.getData() != null) {
                String result = mapper.writeValueAsString(body.getData());
                log.info("【BODY】{}", result);
            }
        }
        log.info(LINE);
    }

    /**
     * 異常處理紀錄
     * 
     * @param point
     * @param error
     */
    @AfterThrowing(pointcut = "loggerAopPointCut()", throwing = "error")
    public void afterThrowLog(JoinPoint point, Throwable error) {
        log.info(LINE);
        log.info("【異常狀態】");
        log.info(LINE);
        log.info("【SIGNATURE】{}", point.getSignature());
        log.info("【EXCEPTION】{}", error);
        log.info(LINE);
    }

    /**
     * 取得 IP 位置
     * 
     * @return
     */
    private String getIp() {
        String ip = request.getHeader("x-foewarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equals(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equals(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equals(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

}
