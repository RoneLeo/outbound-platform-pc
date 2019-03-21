package com.chiyun.outboundplatform.web;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;

/**
 * Created by wazto on 2019/3/21.
 */
@RestController
@Api(description = "文件查看")
@RequestMapping(value = "/files", method = {RequestMethod.GET, RequestMethod.POST})
public class FilesController {
    @Value("${minipropath}")
    String path;

    @RequestMapping("/{filename}")
    public void getfile(@PathVariable String filename, HttpServletResponse response, HttpServletRequest request) throws IOException {
        File file = new File(path + filename);
        if (!file.exists()) {
//            MessageUtils.resultMsg(response, ApiResult.FAILURE("不存在的文件"));
            return;
        }
        String userAgent = request.getHeader("USER-AGENT");
        String newFilename = URLEncoder.encode(filename, "UTF-8").replace("+", " ");
        ;

        if (userAgent != null) {
            if (userAgent.contains("edge") || userAgent.contains("Edge") || userAgent.contains("Trident") || userAgent.contains("trident")) {
                newFilename = URLEncoder.encode(filename, "UTF-8").replace("+", " ");
            } else {
                newFilename = new String(filename.getBytes("UTF-8"), "ISO8859-1");
            }
        }
        try {
            // 以流的形式下载文件。
            InputStream fis = new BufferedInputStream(new FileInputStream(path + filename));
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            fis.close();
            // 清空response
            response.reset();
            // 设置response的Header
            response.addHeader("Content-Disposition", "attachment;filename=" + newFilename);
            response.addHeader("Content-Length", "" + file.length());
            OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
            response.setContentType("application/octet-stream");
            toClient.write(buffer);
            toClient.flush();
            toClient.close();
        } catch (IOException ex) {
            ex.printStackTrace();
//            MessageUtils.resultMsg(response, ApiResult.FAILURE("不存在的文件"));
        }
    }
}
