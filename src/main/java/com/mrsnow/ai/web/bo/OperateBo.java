package com.mrsnow.ai.web.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

/**
 * @Author dongzhen
 * @CreateTime: 2024-12-13  14:54
 **/
@Data
@NoArgsConstructor
public class OperateBo{
    String data;
    String operateType;

    public OperateBo(String data,String operateType){
        this.data=data;
        this.operateType=operateType;
    }
}
