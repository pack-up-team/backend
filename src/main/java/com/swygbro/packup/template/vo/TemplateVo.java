package com.swygbro.packup.template.vo;

import java.util.List;

import com.swygbro.packup.common.vo.CommonVo;

import lombok.Data;

@Data
public class TemplateVo extends CommonVo{
    private int templateNo;
    private int userNo;
    private String UserId;

    List<TempStepVo> stepsList;
}
