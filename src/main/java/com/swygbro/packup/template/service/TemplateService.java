package com.swygbro.packup.template.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.swygbro.packup.template.Mapper.TemplateMapper;
import com.swygbro.packup.template.vo.CateObjVo;
import com.swygbro.packup.template.vo.TempStepObjVo;
import com.swygbro.packup.template.vo.TempStepTextVo;
import com.swygbro.packup.template.vo.TempStepVo;
import com.swygbro.packup.template.vo.TemplateVo;


@Service
@Transactional
public class TemplateService {

    @Autowired
    private TemplateMapper templateMapper;

    @Transactional(rollbackFor = Exception.class)
    public Map<String,Object> TemplateSave(TemplateVo tempVo) {

        Boolean saveStatus = true;

        Map<String,Object> responseMap = new HashMap<>();

        int templateSave = templateMapper.templateSave(tempVo);

        if(templateSave < 1){
            saveStatus = false;
            responseMap.put("status", saveStatus);
            responseMap.put("resposeText", "템플릿 저장시 오류 발생");
            return responseMap;
        }

        for(int i=0;i<tempVo.getStepsList().size();i++){
            int templateSaveStep = templateMapper.templateSaveStep(tempVo.getStepsList().get(i));

            if(templateSaveStep < 1){
                saveStatus = false;
                responseMap.put("status", saveStatus);
                responseMap.put("resposeText", "템플릿 스텝 저장시 오류 발생");
                return responseMap;
            }
            
            if(tempVo.getStepsList().get(i).getStepObjList().size() > 0){

                for(int t=0;t<tempVo.getStepsList().get(i).getStepObjList().size();t++){
                    int templateSaveStepObj = templateMapper.templateSaveStepObj(tempVo.getStepsList().get(i).getStepObjList().get(t));

                    if(templateSaveStepObj < 1){
                        saveStatus = false;
                        responseMap.put("status", saveStatus);
                        responseMap.put("resposeText", "템플릿 스텝 오브젝트 저장시 오류 발생");
                        return responseMap;
                    }
                }

                
            }

            
            
            if(tempVo.getStepsList().get(i).getStepTextList().size() > 0){

                for(int t=0;t<tempVo.getStepsList().get(i).getStepTextList().size();t++){
                    int templateSaveStepText = templateMapper.templateSaveStepText(tempVo.getStepsList().get(i).getStepTextList().get(t));

                    if(templateSaveStepText < 1){
                        saveStatus = false;
                        responseMap.put("status", saveStatus);
                        responseMap.put("resposeText", "템플릿 스텝 텍스트 저장시 오류 발생");
                        return responseMap;
                    }
                }

                
            }
        }

        responseMap.put("status", saveStatus);
        responseMap.put("resposeText", "템플릿 정상 저장");

        return responseMap;
    }

    public List<CateObjVo> getCateTemplateObject(CateObjVo objVo) {
        
        List<CateObjVo> objList = templateMapper.getCateTemplateObject(objVo);

        return objList;
    }

    /**
     * 템플릿 전체 데이터 조회
     */
    public TemplateVo getDetailData(Integer templateNo) {
        
        // 1. 템플릿 기본 정보 조회
        TemplateVo templateVo = templateMapper.getTemplate(templateNo);
        
        if(templateVo == null) {
            return null;
        }
        
        // 2. 스텝 목록 조회
        List<TempStepVo> stepsList = templateMapper.getStepsByTemplateNo(templateNo);
        
        // 3. 각 스텝의 하위 데이터 조회
        for(TempStepVo step : stepsList) {
            Integer stepNo = step.getStep();
            
            // 스텝 객체 목록 조회
            List<TempStepObjVo> stepObjList = templateMapper.getStepObjByStepNo(stepNo,templateNo);
            step.setStepObjList(stepObjList);
            
            // 스텝 텍스트 목록 조회
            List<TempStepTextVo> stepTextList = templateMapper.getStepTextByStepNo(stepNo,templateNo);
            step.setStepTextList(stepTextList);
        }
        
        templateVo.setStepsList(stepsList);
        
        return templateVo;
    }
    
    /**
     * 사용자별 템플릿 목록 조회
     */
    public List<TemplateVo> getTemplatesByUserId(TemplateVo tempVo) {
        
        int page = tempVo.getPage();

        if(page > 0) {
            int pageSize = 4;  // 한 페이지당 4개
            int offset = (page - 1) * pageSize;  // 페이지별 시작 위치 계산
            

            tempVo.setPageSize(pageSize);
            tempVo.setOffset(offset);
        }
        
        List<TemplateVo> templateList = templateMapper.getTemplatesByUserId(tempVo);
        
        return templateList;
    }


}
