package com.swygbro.packup.template.vo;

import java.util.List;

import com.swygbro.packup.common.vo.CommonVo;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TemplateVo extends CommonVo{
    private int templateNo;
    private int userNo;
    private String templateNm;

    List<TempStepVo> stepsList;
}
