package tw.com.demo.config.feign;

import java.lang.reflect.Type;

import com.fasterxml.jackson.databind.ObjectMapper;

import feign.RequestTemplate;
import feign.codec.EncodeException;
import feign.codec.Encoder;

/**
 * Feign Request 編碼器
 */
public class FeignJsonEncoder implements Encoder {

    private final ObjectMapper objectMapper;

    public FeignJsonEncoder(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void encode(Object object, Type bodyType, RequestTemplate template) throws EncodeException {
        try {
            template.body(objectMapper.writeValueAsString(object));
        } catch (Exception e) {
            throw new EncodeException(e.getMessage(), e);
        }
    }

}
