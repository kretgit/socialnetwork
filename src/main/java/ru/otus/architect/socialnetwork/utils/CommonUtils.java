package ru.otus.architect.socialnetwork.utils;

import org.apache.commons.io.IOUtils;
import org.springframework.util.ObjectUtils;

import java.nio.charset.Charset;
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

}
