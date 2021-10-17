package ru.otus.architect.socialnetwork.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public final class Integration {
    public static final String JSON_DATE_FORMAT = "DD.MM.YYYY HH:mm:ss";

    public static final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .setDateFormat(JSON_DATE_FORMAT)
            .create();
    public static final Gson gsonCompact = new GsonBuilder()
            .create();


    public static ToJson toJson(Object target) {
        return new ToJson(target, false);
    }

    public static ToJson toJsonCompact(Object target) {
        return new ToJson(target, true);
    }


    public static class ToJson {
        private final Object target;
        private final boolean compact;

        private ToJson(Object target, boolean compact) {
            this.target = target;
            this.compact = compact;
        }

        @Override
        public String toString() {
            if (compact) {
                return gsonCompact.toJson(target);
            } else {
                return gson.toJson(target);
            }
        }
    }

    public static <T> T fromJson(Class<T> clazz, String json) {
        return gson.fromJson(json, clazz);
    }

    public static Object fromJson(String className, String json) throws ClassNotFoundException {
        return gson.fromJson(json, ClassLoader.getSystemClassLoader().loadClass(className));
    }

    public static <T> List<T> listFromJson(Class<T> clazz, String json) {
        Type listType = TypeToken.getParameterized(ArrayList.class, clazz).getType();
        return gson.fromJson(json, listType);
    }

    public static List<?> listFromJson(String className, String json) throws ClassNotFoundException {
        Class clazz = ClassLoader.getSystemClassLoader().loadClass(className);
        Type listType = TypeToken.getParameterized(ArrayList.class, clazz).getType();
        return gson.fromJson(json, listType);
    }


}
