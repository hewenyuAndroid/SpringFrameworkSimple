package com.example.springmvc_helloworld.controller;

import com.example.springmvc_helloworld.pojo.PersonVO2;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@RestController
public class ResponseTestController {

    /**
     * 会自动将对象转换为json格式
     */
    @ResponseBody   // 把响应的内容，写到响应体中
    @GetMapping("/resp01")
    public PersonVO2 resp01() {
        PersonVO2 personVO2 = new PersonVO2();
        personVO2.setUsername("zhangsan")
                .setPassword("123456")
                .setCellphone("132123456")
                .setGrade("一年级")
                .setAgreement(true)
                .setHobby(new String[]{"吃饭", "睡觉"});
        return personVO2;
    }


    /**
     * 测试文件下载
     * HttpEntity: 拿到整个请求数据
     * ResponseEntity: 拿到整个响应数据 ( 响应头，响应体，状态码 )
     */
    @GetMapping("/download")
    public ResponseEntity<byte[]> download() {
        File file = new File("C:\\software\\idea\\workspace\\fileRepo\\Java多线程-线程池动画01.drawio.png");

        // 解决中文名称乱码问题
        String encoderName = URLEncoder.encode(file.getName());

        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(file);
            // 当前直接读取，文件过大时会有 oom 问题
            byte[] data = inputStream.readAllBytes();

            return ResponseEntity.ok()
                    // 内容类型: 流
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .contentLength(data.length)
                    // Content-Disposition: 内容处理方式
                    .header("Content-Disposition", "attachment; filename=" + encoderName)
                    .body(data);

        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    /**
     * 文件下载，解决文件过大oom的问题提
     */
    @GetMapping("/download2")
    public ResponseEntity<InputStreamResource> download2() throws UnsupportedEncodingException {
        File file = new File("C:\\software\\idea\\workspace\\fileRepo\\Java多线程-线程池动画01.drawio.png");

        // 解决中文名称乱码问题
        String encoderName = URLEncoder.encode(file.getName(), "UTF-8");

        FileInputStream inputStream = null;
        InputStreamResource resource = null;
        try {
            inputStream = new FileInputStream(file);
            resource = new InputStreamResource(inputStream);

            return ResponseEntity.ok()
                    // 内容类型: 流
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .contentLength(inputStream.available())
                    // Content-Disposition: 内容处理方式
                    .header("Content-Disposition", "attachment; filename=" + encoderName)
                    .body(resource);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
