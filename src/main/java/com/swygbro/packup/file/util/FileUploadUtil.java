package com.swygbro.packup.file.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.swygbro.packup.file.vo.FileUploadVo;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class FileUploadUtil {

    private static String uploadPath;
    private static long maxFileSize;
    private static String allowedExtensions;

    @Value("${file.upload.path}")
    public void setUploadPath(String uploadPath) {
        FileUploadUtil.uploadPath = uploadPath;
    }

    @Value("${file.upload.max-size}")
    public void setMaxFileSize(long maxFileSize) {
        FileUploadUtil.maxFileSize = maxFileSize;
    }

    @Value("${file.upload.allowed-extensions}")
    public void setAllowedExtensions(String allowedExtensions) {
        FileUploadUtil.allowedExtensions = allowedExtensions;
    }

    public static FileUploadVo uploadFile(MultipartFile file, String category) throws IOException {
        validateFile(file);
        
        String savedFileName = generateUniqueFileName(file.getOriginalFilename());
        String categoryPath = createCategoryDirectory(category);
        String fullPath = categoryPath + File.separator + savedFileName;
        
        Path destinationPath = Paths.get(fullPath);
        Files.copy(file.getInputStream(), destinationPath);
        
        log.info("파일 업로드 완료: {}", fullPath);
        
        return createFileUploadVo(file, savedFileName, fullPath, category);
    }

    public static List<FileUploadVo> uploadMultipleFiles(MultipartFile[] files, String category) throws IOException {
        List<FileUploadVo> uploadResults = new ArrayList<>();
        
        for (MultipartFile file : files) {
            if (!file.isEmpty()) {
                uploadResults.add(uploadFile(file, category));
            }
        }
        
        return uploadResults;
    }

    public static boolean deleteFile(String filePath) {
        try {
            Path path = Paths.get(filePath);
            boolean deleted = Files.deleteIfExists(path);
            if (deleted) {
                log.info("파일 삭제 완료: {}", filePath);
            } else {
                log.warn("파일이 존재하지 않음: {}", filePath);
            }
            return deleted;
        } catch (IOException e) {
            log.error("파일 삭제 중 오류 발생: {}", e.getMessage());
            return false;
        }
    }

    public static boolean isValidFile(MultipartFile file) {
        try {
            validateFile(file);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private static void validateFile(MultipartFile file) {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("파일이 비어있습니다.");
        }

        if (file.getSize() > maxFileSize) {
            throw new IllegalArgumentException("파일 크기가 제한을 초과했습니다. 최대 크기: " + (maxFileSize / 1024 / 1024) + "MB");
        }

        String fileExtension = getFileExtension(file.getOriginalFilename()).toLowerCase();
        List<String> allowedExtList = Arrays.asList(allowedExtensions.split(","));
        
        if (!allowedExtList.contains(fileExtension)) {
            throw new IllegalArgumentException("허용되지 않는 파일 형식입니다. 허용된 형식: " + allowedExtensions);
        }

        log.info("파일 타입: {}, 크기: {} bytes", file.getContentType(), file.getSize());
    }

    private static String generateUniqueFileName(String originalFileName) {
        String extension = getFileExtension(originalFileName);
        String nameWithoutExtension = getFileNameWithoutExtension(originalFileName);
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        String uuid = UUID.randomUUID().toString().substring(0, 8);
        
        return String.format("%s_%s_%s.%s", nameWithoutExtension, timestamp, uuid, extension);
    }

    private static String createCategoryDirectory(String category) throws IOException {
        String categoryPath = uploadPath + File.separator + category;
        Path path = Paths.get(categoryPath);
        
        if (!Files.exists(path)) {
            Files.createDirectories(path);
            log.info("디렉토리 생성: {}", categoryPath);
        }
        
        return categoryPath;
    }

    private static FileUploadVo createFileUploadVo(MultipartFile file, String savedFileName, String fullPath, String category) {
        FileUploadVo vo = new FileUploadVo();
        vo.setOriginalFileName(file.getOriginalFilename());
        vo.setSavedFileName(savedFileName);
        vo.setFilePath(fullPath);
        vo.setFileUrl("/static/" + category + "/" + savedFileName);
        vo.setFileSize(file.getSize());
        vo.setContentType(file.getContentType());
        vo.setCategory(category);
        vo.setUploadDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        
        return vo;
    }

    private static String getFileExtension(String fileName) {
        if (fileName == null || fileName.isEmpty()) {
            return "";
        }
        int lastDotIndex = fileName.lastIndexOf('.');
        return lastDotIndex > 0 ? fileName.substring(lastDotIndex + 1) : "";
    }

    private static String getFileNameWithoutExtension(String fileName) {
        if (fileName == null || fileName.isEmpty()) {
            return "";
        }
        int lastDotIndex = fileName.lastIndexOf('.');
        return lastDotIndex > 0 ? fileName.substring(0, lastDotIndex) : fileName;
    }
}