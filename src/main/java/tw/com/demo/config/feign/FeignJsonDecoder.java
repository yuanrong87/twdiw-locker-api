package tw.com.demo.config.feign;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Collections;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import com.fasterxml.jackson.databind.ObjectMapper;

import feign.Response;
import feign.codec.Decoder;
import tw.com.demo.exception.CustomException;
import tw.com.demo.type.ReturnCodeType;

/**
 * Feign Response 解碼器
 */
public class FeignJsonDecoder implements Decoder {

    private final ObjectMapper objectMapper;

    public FeignJsonDecoder(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public Object decode(Response response, Type type) throws IOException {

        if (HttpStatus.OK.value() == response.status() || HttpStatus.CREATED.value() == response.status()) {
            Collection<String> contentTypes = response.headers().getOrDefault("Content-Type", Collections.emptyList());
            if (contentTypes.stream().anyMatch(ct -> ct.contains(MediaType.TEXT_HTML_VALUE))) {
                return new String(response.body().asInputStream().readAllBytes(), StandardCharsets.UTF_8);
            }

            try (InputStream in = response.body().asInputStream()) {
                return objectMapper.readValue(in, objectMapper.constructType(type));
            }
        }

        throw new CustomException(ReturnCodeType.ERROR_CALL_API);
    }

}
