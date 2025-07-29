package com.swygbro.packup.template.Mapper;

import java.util.List;

import com.swygbro.packup.template.vo.CateObjVo;
import com.swygbro.packup.template.vo.TempStepObjVO;
import com.swygbro.packup.template.vo.TempStepTextVo;
import com.swygbro.packup.template.vo.TempStepVo;
import com.swygbro.packup.template.vo.TemplateVo;

public interface TemplateMapper {

    int templateSave(TemplateVo tempVo);

    List<CateObjVo> getCateTemplateObject(CateObjVo objVo);

    int templateSaveStep(TempStepVo tempVo);

    int templateSaveStepObj(TempStepObjVO tempStepObjVO);

    int templateSaveStepText(TempStepTextVo tempStepTextVo);

    TemplateVo getTemplate(Integer templateNo);

    List<TempStepVo> getStepsByTemplateNo(Integer templateNo);

    List<TempStepObjVO> getStepObjByStepNo(Integer stepNo, Integer templateNo);

    List<TempStepTextVo> getStepTextByStepNo(Integer stepNo, Integer templateNo);

    List<TemplateVo> getTemplatesByUserId(TempStepVo tempVo);

}
