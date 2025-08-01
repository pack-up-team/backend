package com.swygbro.packup.template.mapper;

import java.util.List;

import com.swygbro.packup.template.vo.CateObjVo;
import com.swygbro.packup.template.vo.TempStepObjVo;
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

    int templateUpdate(TemplateVo tempVo);

    int templateUpdateStep(TempStepVo tempStepVo);

    int templateUpdateStepObj(TempStepObjVo tempStepObjVo);

    int templateUpdateStepText(TempStepTextVo tempStepTextVo);

    void deleteTempalteStepObj(@Param("templateNo")int templateNo);

    void deleteTempalteStepText(@Param("templateNo")int templateNo);

    int deleteTemplate(int tempateNo);

    int deleteStepTemplate(int tempateNo);

    int deleteTempalteStepObjInt(int tempateNo);

	  int deleteTempalteStepTextInt(int tempateNo);

    int getTemplateStepNo(@Param("templateNo") int templateNo,@Param("step") int step);

}
