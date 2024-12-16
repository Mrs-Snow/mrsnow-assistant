package com.mrsnow.ai.commons;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author MrSnow *** dz
 * @CreateTime: 2023-12-07  09:26
 * 如果需要多线程传递参数 将ThreadLocal升级为：TransmittableThreadLocal
 **/
public class ContextUtil {
    private static final ThreadLocal<Map<String, Object>> THREAD_LOCAL = new ThreadLocal<>();

    public ContextUtil() {
    }

    public static void putAll(Map<String, String> map) {
        map.forEach((k, v) -> {
            set(k, v);
        });
    }

    public static void setSecOrgId(Long secOrgId) {
        if (ObjectUtil.isEmpty(secOrgId) || StrUtil.isBlankOrUndefined(secOrgId.toString())) {
            return;
        }
        Map<String, Object> map = getLocalMap();
        map.put("SECOND_ORG_ID", secOrgId);
    }

    public static Long getSecOrgId() {
        Map<String, Object> map = getLocalMap();
        Object second_org_id = map.get("SECOND_ORG_ID");
        if (second_org_id != null) {
            return Convert.toLong(second_org_id);
        } else {
            return 101113242L;
        }
    }

    public static void setProjectId(Long secOrgId) {
        if (ObjectUtil.isEmpty(secOrgId) || StrUtil.isBlankOrUndefined(secOrgId.toString())) {
            return;
        }
        Map<String, Object> map = getLocalMap();
        map.put("PROJECT_ID", secOrgId);
    }

    public static Long getProjectId() {
        Map<String, Object> map = getLocalMap();
        Object second_org_id = map.get("PROJECT_ID");
        if (second_org_id != null) {
            return Convert.toLong(second_org_id);
        }
        return null;
    }

    public static void set(String key, Object value) {
        if (ObjectUtil.isEmpty(value) || StrUtil.isBlankOrUndefined(value.toString())) {
            return;
        }
        Map<String, Object> map = getLocalMap();
        map.put(key, value);
    }

    public static Object get(String key) {
        Map<String, Object> map = getLocalMap();
        return map.get(key);
    }

    public static <T> T get(String key, Class<T> type) {
        Map<String, Object> map = getLocalMap();
        return Convert.convert(type, map.get(key));
    }

    public static Map<String, Object> getLocalMap() {
        Map<String, Object> map = THREAD_LOCAL.get();
        if (map == null) {
            map = new ConcurrentHashMap<>(10);
            THREAD_LOCAL.set(map);
        }
        return map;
    }

    public static void setSqlSessionKey(String key) {
        //空串毫无意义而且会引发问题，不如往空指针异常走，而且几乎不可能这里为空，除非拦截器失效
        if (key == null || key.length() == 0) {
            return;
        }
        Map<String, Object> localMap = getLocalMap();
        localMap.put("sqlSessionKey", key);
    }

    public static String getSqlSessionKey() {
        Map<String, Object> localMap = getLocalMap();
        return (String) localMap.get("sqlSessionKey");
    }


    public static void remove() {
        THREAD_LOCAL.remove();
    }

}
