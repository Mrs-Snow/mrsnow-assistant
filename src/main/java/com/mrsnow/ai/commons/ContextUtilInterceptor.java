package com.mrsnow.ai.commons;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.AsyncHandlerInterceptor;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author MrSnow *** dz
 * @CreateTime: 2023-12-07  09:10
 * 关键！！！
 * ThreadLocal工具，在此remove
 * 为批量操作类关闭会话
 **/
@Component
public class ContextUtilInterceptor implements AsyncHandlerInterceptor {
    private Logger logger = LoggerFactory.getLogger(ContextUtilInterceptor.class);
    private static final String SQL_SESSION = "SQL_SESSION";

    /**
     * 这里可以做许多事，比如解析请求头中的内容，保存在本次请求变量里
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return true;
    }

    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        ContextUtil.remove();
        logger.debug("ContextUtilInterceptor：：：：： closeSession()---->remove()----->已执行");
    }
}
