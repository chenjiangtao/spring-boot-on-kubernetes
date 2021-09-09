package cn.topstream.app.utils;

import java.util.*;

public class ThreadLocalUtil {

    private static final ThreadLocal<Map<String, Object>> threadLocal =
            ThreadLocal.withInitial(() -> new HashMap<>(16));


    public static <T> T get(String key) {
        Map map = (Map)threadLocal.get();
        return (T)map.get(key);
    }

    public static <T> T get(String key,T defaultValue) {
        Map map = (Map)threadLocal.get();
        return (T)map.get(key) == null ? defaultValue : (T)map.get(key);
    }

    public static void set(String key, Object value) {
        Map map = (Map)threadLocal.get();
        map.put(key, value);
    }

    public static void set(Map<String, Object> keyValueMap) {
        Map map = (Map)threadLocal.get();
        map.putAll(keyValueMap);
    }

    public static void remove() {
        threadLocal.remove();
    }

    public static <T> T remove(String key) {
        Map map = (Map)threadLocal.get();
        return (T)map.remove(key);
    }

    public static void clear(String prefix) {
        if (prefix == null) {
            return;
        }
        Map map = (Map) threadLocal.get();
        Set<Map.Entry> set = map.entrySet();
        List<String> removeKeys = new ArrayList<>();

        for (Map.Entry entry : set) {
            Object key = entry.getKey();
            if (key instanceof String) {
                if (((String) key).startsWith(prefix)) {
                    removeKeys.add((String) key);
                }
            }
        }
        for (String key : removeKeys) {
            map.remove(key);
        }
    }

}
