package com.mrsnow.ai.tools;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mrsnow.ai.services.ReplaceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;

import java.util.function.Function;

/**
 * @Author dongzhen
 * @CreateTime: 2024-12-13  10:25
 * 查询系统工具集
 **/
@Configuration
@Slf4j
public class SearchTools {
    @Autowired
    private ReplaceService replaceService;

    public record SearchRequest(String name,String empName){}

    public record ReplaceRequest(String talkId,String targetName){}
    public record CompareRequest(int number1,int number2){}
    @JsonInclude(Include.NON_NULL)
    public record Route(String route,String type){}

    @Bean
    @Description("跳转到某个功能的页面")
    public Function<ReplaceRequest, String> replaceTo(){
        return request->{
            try {
                replaceService.replaceTo(request.talkId,request.targetName());
                return "";
            } catch (Exception e) {
                log.error("跳转失败",e);
                throw new RuntimeException(e);
            }
        };
    }

    @Bean
    @Description("比较两个数字哪个更大")
    public Function<CompareRequest, String> compareNumber(){
        return request->{
            try {
                if (request.number1() > request.number2()){
                    return request.number1()+"大于"+request.number2();
                }else {
                    return request.number1()+"小于"+request.number2();
                }
            } catch (Exception e) {
                log.error("比较失败",e);
                throw new RuntimeException(e);
            }
        };
    }

    @Bean
    @Description("查找某个功能的数据")
    public Function<ReplaceRequest, String> searchInfo(){
        return request->{
            try {
                replaceService.replaceTo(request.talkId,request.targetName());
                return "";
            } catch (Exception e) {
                log.error("查找失败",e);
                throw new RuntimeException(e);
            }
        };
    }
}
