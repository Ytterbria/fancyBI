package com.ytterbria.fancyback.utils;


import cn.hutool.core.io.FileUtil;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;


public class FileVertifyUtils {

    public static boolean isFileValid(MultipartFile file){
        final long MAX_FILE_SIZE = 1024 * 1024 * 5L; // 10MB
        if (file.isEmpty())
            return false;
        if (file.getSize() > MAX_FILE_SIZE)
            return false;
        final List<String> ALLOWED_EXTENSIONS = Arrays.asList("jpg", "jpeg", "png", "gif","csv","xlsx");
        String suffix = FileUtil.getSuffix(file.getOriginalFilename());
        return ALLOWED_EXTENSIONS.contains(suffix);
    }
}
