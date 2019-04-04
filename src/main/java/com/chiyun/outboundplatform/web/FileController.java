package com.chiyun.outboundplatform.web;

import com.chiyun.outboundplatform.entity.FileEntity;
import com.chiyun.outboundplatform.repository.FileRepository;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;

@Api(description = "文件管理")
@RestController
@RequestMapping(value = "/file", method = {RequestMethod.POST, RequestMethod.GET})
public class FileController {

    @Resource
    private FileRepository fileRepository;

    public FileEntity addFile(HttpServletRequest request, MultipartFile file) {
        String filename = file.getOriginalFilename();
        String path = request.getSession().getServletContext().getRealPath("/upload/");
        File dest = new File(path + filename);
        if(!dest.getParentFile().exists()){
            dest.getParentFile().mkdir();
        }
        FileEntity fileEntity = new FileEntity();

        try {
            file.transferTo(dest);
            fileEntity.setWjmc(filename);
            fileEntity.setWjdz("upload/" + filename);
            fileRepository.save(fileEntity);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileEntity;
    }
}
