package com.chiyun.outboundplatform.web;

import com.chiyun.outboundplatform.common.ApiResult;
import com.chiyun.outboundplatform.entity.FileEntity;
import com.chiyun.outboundplatform.repository.FileRepository;
import com.chiyun.outboundplatform.utils.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.*;

@Api(description = "文件管理")
@RequestMapping(value = "/file", method = {RequestMethod.POST, RequestMethod.GET})
@RestController
public class FileController {

    @Resource
    private FileRepository fileRepository;

    @ApiOperation("添加")
    @RequestMapping("/add")
    @ApiImplicitParam(name = "rwid", value = "任务id", dataType = "Integer", paramType = "query")
    public FileEntity addFile(Integer rwid, MultipartFile file, HttpServletRequest request) {
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
            fileEntity.setRwid(rwid);
            fileEntity.setWjdz("upload/" + filename);
            fileRepository.save(fileEntity);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileEntity;
    }

    @ApiOperation("通过任务id查询")
    @RequestMapping("/findAllByRwid")
    @ApiImplicitParam(name = "rwid", value = "任务id", dataType = "Integer", paramType = "query")
    public ApiResult<Object> findAllByRwid(Integer rwid) {
        List<FileEntity> list = null;
        if (rwid == null) {
            list = fileRepository.findAll();
        } else {
            list = fileRepository.findAllByRwid(rwid);
        }
        return ApiResult.SUCCESS(list);
    }


    /**
     *  将文件分类查询显示
     */
    public Map get(String ids) {
        Map<String, List> map = new HashMap<>();
        List<String> listPhoto = new ArrayList<>();
        List<String> listVideo = new ArrayList<>();
        List<String> listAudio = new ArrayList<>();
        if (StringUtil.isNotNull(ids)) {
            String[] idss = ids.split(",");
            for (int i = 0; i < idss.length; i ++) {
                Integer id = Integer.parseInt(idss[i]);
                FileEntity entity = null;
                Optional<FileEntity> optional = fileRepository.findById(id);
                if (optional.isPresent()) {
                    entity = optional.get();
                    String wedz = entity.getWjdz();
                    // 获取后缀名
                    String hzm = wedz.substring(wedz.lastIndexOf(".") + 1).toUpperCase();

                    if (hzm.equals("BMP") | hzm.equals("JPG") | hzm.equals("JPEG") | hzm.equals("PNG") | hzm.equals("GIF")) {
                        // 图片格式：BMP、JPG、JPEG、PNG、GIF
                        listPhoto.add(wedz);
                    } else if (hzm.equals("AVI") | hzm.equals("MOV") | hzm.equals("RMVB") | hzm.equals("RM")
                            | hzm.equals("FLV") | hzm.equals("MP4") | hzm.equals("3GP") | hzm.equals("WEBM")) {
                        // 视频格式：AVI、mov、rmvb、rm、FLV、mp4、3GP
                        listVideo.add(wedz);
                    } else if (hzm.equals("AAC") | hzm.equals("WAV") | hzm.equals("MIDI") | hzm.equals("CDA") | hzm.equals("MP3") | hzm.equals("WMA")) {
                        // 音频格式：WAV 、MIDI、CDA、MP3、WMA、MP4
                        listAudio.add(wedz);
                    }
                }
            }

        }
        map.put("photo", listPhoto);
        map.put("video", listVideo);
        map.put("audio", listAudio);
        return map;
    }
}
