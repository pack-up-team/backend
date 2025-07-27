package com.swygbro.packup.file.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileUploadVo {
    private String originalFileName;
    private String savedFileName;
    private String filePath;
    private String fileUrl;
    private Long fileSize;
    private String contentType;
    private String category;
    private String uploadDate;
}