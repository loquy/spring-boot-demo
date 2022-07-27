package com.example.mongodb.controller;

import com.example.mongodb.utils.MongoFileUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * The type Mongo file controller.
 *
 * @author loquy
 */
@RestController
@RequestMapping(value = "/mongoFile")
public class MongoFileController {

    /**
     * 上传文件
     *
     * @param multipartFile the multipart file
     * @return the string
     */
    @PostMapping("/upload")
    @ResponseBody
    public String uploadFile(@RequestParam("file") MultipartFile multipartFile) {
        String originFilename = multipartFile.getOriginalFilename();
        String fileId;
        try {
            fileId = MongoFileUtils.uploadFileToGridFs(StringUtils.getFilename(originFilename), multipartFile.getInputStream());
        } catch (Exception e) {
            e.printStackTrace();
            return "上传失败，" + e.getMessage();
        }
        return "上传成功，文件ID：" + fileId;
    }

    /**
     * 删除文件
     *
     * @param fileId the file id
     * @return the string
     */
    @PostMapping("/remove")
    @ResponseBody
    public String removeFile(@RequestParam("fileId") String fileId) {
        try {
            MongoFileUtils.deleteByObjectId(fileId);
        } catch (Exception e) {
            e.printStackTrace();
            return "删除失败，" + e.getMessage();
        }

        return "删除成功";
    }

    /**
     * 显示图片
     *
     * @param fileId   the file id
     * @param response the response
     */
    @GetMapping(value = "/showImage/{fileId}")
    public Object showImage(@PathVariable(name = "fileId") String fileId, HttpServletResponse response)  {
        try {
            MongoFileUtils.showImage(fileId, response);
        } catch (Exception e) {
            e.printStackTrace();
            return "显示失败，" + e.getMessage();
        }

        return "";
    }

    /**
     * 下载附件
     *
     * @param fileId   the file id
     * @param response the response
     */
    @GetMapping(value = "/download/{fileId}")
    public Object download(@PathVariable(name = "fileId") String fileId, HttpServletResponse response) {
        try {
            MongoFileUtils.downloadFile(fileId, response);
        } catch (Exception e) {
            e.printStackTrace();
            return "下载失败，" + e.getMessage();
        }
        return "";
    }
}
