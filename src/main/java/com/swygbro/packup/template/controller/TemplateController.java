package com.swygbro.packup.template.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.swygbro.packup.template.service.TemplateService;
import com.swygbro.packup.template.vo.CateObjVo;
import com.swygbro.packup.template.vo.StepObjVo;
import com.swygbro.packup.template.vo.StepTextVo;
import com.swygbro.packup.template.vo.StepVo;
import com.swygbro.packup.template.vo.TemplateVo;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@NoArgsConstructor
@AllArgsConstructor
public class TemplateController {

    private TemplateService templateService;

    @PostMapping("/getCateTemplateObject")
    public ResponseEntity<Map<String,Object>> getCateTemplateObject(@RequestBody CateObjVo ObjVo){
        List<CateObjVo> teplateObj = templateService.getCateTemplateObject(ObjVo);

        Map<String, Object> response = new HashMap<>();

        response.put("objList", teplateObj);
        response.put("responseText", "success");

        return ResponseEntity.ok(response);
    }
    

    @PostMapping("/templateSave")
    public ResponseEntity<Map<String, Object>> getTemplateSave(@RequestBody TemplateVo tempVo){
        
        Map<String, Object> teplateSaveMap = templateService.TemplateSave(tempVo);

        Map<String, Object> response = new HashMap<>();

        if(Boolean.TRUE.equals(teplateSaveMap.get("status"))) {
            response.put("status", "success");
            return ResponseEntity.ok(response);
        }else{
            response.put("status", "fail");
            return ResponseEntity.badRequest().body(response);
        }

        
    }

    @PostMapping("/getDetailData")
    public ResponseEntity<Map<String, Object>> getDetailData(@RequestBody TemplateVo tempVo) {
        Map<String, Object> response = new HashMap<>();
        tempVo = templateService.getDetailData(tempVo.getTemplateNo());

        response.put("templateData", tempVo);
        response.put("responseText", "success");

        return ResponseEntity.ok(response);
    }

    @PostMapping("/getUserTemplateDataList")
    public ResponseEntity<Map<String, Object>> getUserTemplateDataList(@RequestBody TemplateVo tempVo) {
        Map<String, Object> response = new HashMap<>();
        tempVo = templateService.getTemplatesByUserId(tempVo);

        response.put("templateData", tempVo);
        response.put("responseText", "success");

        return ResponseEntity.ok(response);
    }
    
}
