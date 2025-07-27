package com.swygbro.packup.template.vo;

import java.util.Date;
import java.util.List;

import com.swygbro.packup.common.vo.CommonVo;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TempStepVo extends CommonVo{
    private int templateStepNo;                     // 유저 템플릿 스텝 번호
    private int templateNo;
    private int step;                               // 스텝 단계
    private Date alarmDt;                           // 알람 요청 일자 및 시간, REPEAT_TYPE이 단발성(FALSE)일때 저장
    private Boolean repeatType;                     // 반복(TRUE), 단발성(FALSE)
    private String alarmRepeatDay;                  // 반복 알람일(예시 월,화,수...), REPEAT_TYPE이 반복(TRUE)일 때만 저장
    private String alarmTime;                       // 알람 요청 시간, REPEAT_TYPE이 단발성(FALSE)일때 저장
    private Float stepX;                        // 스텝의 x 위치 값         
    private Float stepY;                        // 스텝의 y 위치 값

    // 배열 형태의 하위 객체들
    private List<TempStepObjVo> stepObjList;
    private List<TempStepTextVo> stepTextList;
}
