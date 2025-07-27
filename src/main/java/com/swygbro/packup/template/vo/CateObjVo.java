package com.swygbro.packup.template.vo;

import com.swygbro.packup.common.vo.CommonVo;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CateObjVo extends CommonVo{
    private int objNo;
    private int cateNo;
    private String objNm;
}
