package com.swygbro.packup.template.vo;

import com.swygbro.packup.common.vo.CommonVo;

import lombok.Data;

@Data
public class TempStepObjVo extends CommonVo{
    private int tempUserObjNo;              // 템플릿 물품 번호
    private int templateStepNo;             // 템플릿 스탭 번호
    private int cateNo;                     // 카테고리 번호
    private int objNo;                      // 물품번호
    private int objCnt;                     // 물품 개수
    private String useYn;                   // 사용여부
    private String delYn;                   // 삭제여부
    private int locNum;                     // 스텝에 존재하는 오브젝트 위치 값
}
