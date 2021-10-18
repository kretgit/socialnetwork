package ru.otus.architect.socialnetwork.utils;

import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.ObjectUtils;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class CommonUtils {

    private static Map<String, String> map = new HashMap<>();

    public static String resourceAsString(String name) {
        String result = map.get(name);
        if (result != null) {
            return result;
        }
        try {
            result = IOUtils.toString(CommonUtils.class.getClassLoader().getResourceAsStream(name), Charset.forName("UTF-8"));
            map.put(name, result);
            return result;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T alterValue(T value, T alter) {
        return ObjectUtils.isEmpty(value) ? alter : value;
    }

    @SuppressWarnings("noinspection SqlResolve")
    public static String getNextId(JdbcTemplate jdbcTemplate, EntityType type) {
        jdbcTemplate.update("update sequence set generated_value = generated_value + 1 where entity_type = '" + type.getSeqType() + "'");
        return jdbcTemplate.queryForObject(
                "select concat(entity_type, generated_value) as id from sequence", String.class);
    }

    public static String getLoginFromHeaders(HttpHeaders httpHeaders) {
        String rawAuthData = httpHeaders.containsKey("Authorization") ? httpHeaders.get("Authorization").get(0) : null;
        if (rawAuthData == null) {
            return null;
        }

        String base64Data = rawAuthData.substring("Basic".length()).trim();
        byte[] credDecoded = Base64.getDecoder().decode(base64Data);
        String credentials = new String(credDecoded, StandardCharsets.UTF_8);
        return credentials.split(":")[0];
    }

}
