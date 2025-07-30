package com.swygbro.packup.template.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.swygbro.packup.template.mapper.TemplateMapper;
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
    public Map<String,Object> templateSave(TemplateVo tempVo) {

        Boolean saveStatus = true;

        Map<String,Object> responseMap = new HashMap<>();

        int templateSave = templateMapper.templateSave(tempVo);

        int newTemplateNo = tempVo.getTemplateNo();

        if(templateSave < 1){
            saveStatus = false;
            responseMap.put("status", saveStatus);
            responseMap.put("resposeText", "템플릿 저장시 오류 발생");
            return responseMap;
        }

        for(int i=0;i<tempVo.getStepsList().size();i++){
            tempVo.getStepsList().get(i).setTemplateNo(newTemplateNo);
            int templateSaveStep = templateMapper.templateSaveStep(tempVo.getStepsList().get(i));

            if(templateSaveStep < 1){
                saveStatus = false;
                responseMap.put("status", saveStatus);
                responseMap.put("resposeText", "템플릿 스텝 저장시 오류 발생");
                return responseMap;
            }

            if(tempVo.getStepsList().get(i).getStepObjList().size() > 0){

                for(int t=0;t<tempVo.getStepsList().get(i).getStepObjList().size();t++){

                    tempVo.getStepsList().get(i).getStepObjList().get(t).setTemplateNo(newTemplateNo);
                    tempVo.getStepsList().get(i).getStepObjList().get(t).setStep(tempVo.getStepsList().get(i).getStep());
                    tempVo.getStepsList().get(i).getStepObjList().get(t).setTemplateStepNo(tempVo.getStepsList().get(i).getTemplateStepNo());

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

                    tempVo.getStepsList().get(i).getStepTextList().get(t).setTemplateNo(newTemplateNo);
                    tempVo.getStepsList().get(i).getStepTextList().get(t).setStep(tempVo.getStepsList().get(i).getStep());
                    tempVo.getStepsList().get(i).getStepTextList().get(t).setTemplateStepNo(tempVo.getStepsList().get(i).getTemplateStepNo());

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

        System.out.println("responseMap : "+responseMap);

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

            System.out.println("stepNo : "+stepNo);

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

    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> templateUpdate(TemplateVo tempVo) {
        Boolean saveStatus = true;

        Map<String,Object> responseMap = new HashMap<>();

        int templateSave = templateMapper.templateUpdate(tempVo);

        int newTemplateNo = tempVo.getTemplateNo();

        if(templateSave < 1){
            saveStatus = false;
            responseMap.put("status", saveStatus);
            responseMap.put("resposeText", "템플릿 저장시 오류 발생");
            return responseMap;
        }

        templateMapper.deleteTempalteStepObj(tempVo.getTemplateNo());
        templateMapper.deleteTempalteStepText(tempVo.getTemplateNo());

        for(int i=0;i<tempVo.getStepsList().size();i++){
            tempVo.getStepsList().get(i).setTemplateNo(tempVo.getTemplateNo());
            int step = i+1;
            int templateStepNo = templateMapper.getTemplateStepNo(tempVo.getTemplateNo(), step);
            tempVo.getStepsList().get(i).setTemplateStepNo(templateStepNo);
            int templateSaveStep = templateMapper.templateUpdateStep(tempVo.getStepsList().get(i));

            if(templateSaveStep < 1){
                saveStatus = false;
                responseMap.put("status", saveStatus);
                responseMap.put("resposeText", "템플릿 스텝 저장시 오류 발생");
                return responseMap;
            }

            if(tempVo.getStepsList().get(i).getStepObjList().size() > 0){

                for(int t=0;t<tempVo.getStepsList().get(i).getStepObjList().size();t++){

                    tempVo.getStepsList().get(i).getStepObjList().get(t).setTemplateNo(tempVo.getTemplateNo());
                    tempVo.getStepsList().get(i).getStepObjList().get(t).setStep(tempVo.getStepsList().get(i).getStep());
                    tempVo.getStepsList().get(i).getStepObjList().get(t).setTemplateStepNo(templateStepNo);
                 
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

                    tempVo.getStepsList().get(i).getStepTextList().get(t).setTemplateNo(tempVo.getTemplateNo());
                    tempVo.getStepsList().get(i).getStepTextList().get(t).setStep(tempVo.getStepsList().get(i).getStep());
                    tempVo.getStepsList().get(i).getStepTextList().get(t).setTemplateStepNo(templateStepNo);
                    
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

        System.out.println("responseMap : "+responseMap);

        return responseMap;
	}

  @Transactional(rollbackFor = Exception.class)
	public Map<String, Object> templateDelete(TemplateVo tempVo) {
		
		Boolean saveStatus = true;

		Map<String,Object> responseMap = new HashMap<>();
		
		int templateNo = tempVo.getTemplateNo();
		
		
		
		
		
		int tempObjDeleteCnt = templateMapper.deleteTempalteStepObjInt(templateNo);
		if(tempObjDeleteCnt < 1){
			saveStatus = false;
			responseMap.put("status", saveStatus);
			responseMap.put("resposeText", "템플릿 스탭 오브젝트 삭제시 오류 발생");
			return responseMap;
		}
		
		int tempTextDeleteCnt = templateMapper.deleteTempalteStepTextInt(templateNo);
		if(tempTextDeleteCnt < 1){
			saveStatus = false;
			responseMap.put("status", saveStatus);
			responseMap.put("resposeText", "템플릿 스탭 텍스트 삭제시 오류 발생");
			return responseMap;
		}

        int tempStepDeleteCnt = templateMapper.deleteStepTemplate(templateNo);
		if(tempStepDeleteCnt < 1){
			saveStatus = false;
			responseMap.put("status", saveStatus);
			responseMap.put("resposeText", "템플릿 스탭 삭제시 오류 발생");
			return responseMap;
		}

        int tempDeleteCnt = templateMapper.deleteTemplate(templateNo);
		
		if(tempDeleteCnt < 1){
			saveStatus = false;
			responseMap.put("status", saveStatus);
			responseMap.put("resposeText", "템플릿 삭제시 오류 발생");
			return responseMap;
		}
		
		responseMap.put("status", saveStatus);
   }

}