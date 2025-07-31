package com.swygbro.packup.file.controller;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.PathResource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.core.io.Resource;
import com.swygbro.packup.file.service.FileService;
import com.swygbro.packup.file.vo.AttachFileVo;
import com.swygbro.packup.file.vo.FileUploadVo;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/files")
@Slf4j
public class FileController {

    @Autowired
    private FileService fileService;

    @PostMapping("/insertFile")
    public ResponseEntity<AttachFileVo> updateFile(AttachFileVo fileVo) {
        
        try {
            AttachFileVo result = fileService.insertFile(fileVo);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            log.error("파일 업데이트 중 오류 발생: {}", e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/image/{refNo}")
    public ResponseEntity<Resource> getImage(@PathVariable int refNo, @RequestParam String fileCate1, @RequestParam String fileCate2) {

        System.out.println("refNo : "+refNo);
        System.out.println("fileCate1 : "+fileCate1);
        System.out.println("fileCate2 : "+fileCate2);
        // 1. DB에서 refNo로 파일 정보 조회
        AttachFileVo files = fileService.getFilesByRefNo(refNo,fileCate1,fileCate2);

        // 2. 첫 번째 파일의 실제 경로 가져옴
        Path filePath = Paths.get(files.getFilePath()); // 실제 파일 경로
        
        System.out.println("filePath : "+filePath);

        // 3. 파일을 브라우저로 전송
        Resource resource = new PathResource(filePath);

        
        System.out.println("resource : "+resource);
        return ResponseEntity.ok().body(resource);
    }
}