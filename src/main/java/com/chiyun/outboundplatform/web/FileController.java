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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    /**
     *  将文件分类查询显示
     */
    public Map get(String ids) {
        Map<String, List> map = new HashMap<>();
        List<String> listPhoto = new ArrayList<>();
        List<String> listVideo = new ArrayList<>();
        List<String> listAudio = new ArrayList<>();
        String[] idss = ids.split(",");
        for (int i = 0; i < idss.length; i ++) {
            Integer id = Integer.parseInt(idss[i]);
            FileEntity entity = fileRepository.findById(id).get();
            String wedz = entity.getWjdz();
            // 获取后缀名
            String hzm = wedz.substring(wedz.lastIndexOf(".")).toUpperCase();

            if (hzm.equals("BMP") | hzm.equals("JPG") | hzm.equals("JPEG") | hzm.equals("PNG") | hzm.equals("GIF")) {
                // 图片格式：BMP、JPG、JPEG、PNG、GIF
                listPhoto.add(wedz);
            } else if (hzm.equals("AVI") | hzm.equals("MOV") | hzm.equals("RMVB") | hzm.equals("RM")
                    | hzm.equals("FLV") | hzm.equals("MP4") | hzm.equals("3GP")) {
                // 视频格式：AVI、mov、rmvb、rm、FLV、mp4、3GP
                listVideo.add(wedz);
            } else if (hzm.equals("WAV") | hzm.equals("MIDI") | hzm.equals("CDA") | hzm.equals("MP3") | hzm.equals("WMA")) {
                // 音频格式：WAV 、MIDI、CDA、MP3、WMA、MP4
                listAudio.add(wedz);
            }
        }
        map.put("photo", listPhoto);
        map.put("video", listVideo);
        map.put("audio", listAudio);
        return map;
    }
}
