package com.chiyun.outboundplatform.utils;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class FileUtil {

    /**
     *  上传文件
     */
    public static String addFile(HttpServletRequest request, String wjlj, MultipartFile file) throws IOException {
        String path = request.getSession().getServletContext().getRealPath(wjlj);
        String realPath = System.currentTimeMillis() + file.getOriginalFilename().substring(file.getOriginalFilename().indexOf("."));
        File dest = new File(path + realPath);
        if(!dest.getParentFile().exists()){
            dest.getParentFile().mkdir();
        }
        file.transferTo(dest);
        return realPath;
    }

    /**
     *  上传多个文件
     */
    public static List<MultipartFile> getfile(HttpServletRequest request) {
        List<MultipartFile> files = null;
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        if (multipartResolver.isMultipart(request)) {
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;

            files = multiRequest.getFiles("file");
        }
        return files;
    }


}
