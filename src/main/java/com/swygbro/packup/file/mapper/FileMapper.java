package com.swygbro.packup.file.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.swygbro.packup.file.vo.AttachFileVo;
import com.swygbro.packup.file.vo.FileUploadVo;

@Mapper
public interface FileMapper {
    
    int insertAttachFile(AttachFileVo fileVo);
    
    AttachFileVo selectFilesByRefNo(@Param("refNo") int refNo, @Param("fileCate1") String fileCate1, @Param("fileCate2") String fileCate2);
    
    int updateFileDelYn(int fileNo);
    
    int deleteFile(int fileNo);
}