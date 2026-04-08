package com.sunnao.qianlian.framework.web.config;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

/**
 * Jackson 全局序列化配置
 *
 * <p>本项目的统一序列化策略：
 * <br>- 统一时区 GMT+8 统一日期格式 yyyy-MM-dd HH:mm:ss
 * <br>- Long/BigInteger 序列化为字符串，避免前端精度丢失
 * <br>- 禁用 WRITE_DATES_AS_TIMESTAMPS，避免日期输出为时间戳</p>
 *
 * @author sunnao
 */
@Configuration
public class JacksonConfig {

    /**
     * 全局 JsonMapper
     *
     * <p>由 Spring Boot 自动装配到 Jackson 相关的 HttpMessageConverter 中，作为全局 JSON 序列化/反序列化
     * 行为的唯一入口。</p>
     */
    @Bean
    public JsonMapper objectMapper() {
        return JsonMapper.builder()
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                .defaultDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"))
                .defaultTimeZone(TimeZone.getTimeZone("GMT+8"))
                .addModule(new SimpleModule()
                        .addSerializer(Long.class, ToStringSerializer.instance)
                        .addSerializer(BigInteger.class, ToStringSerializer.instance)
                )
                .build();
    }

}
