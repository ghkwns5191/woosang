package com.example.demo.home;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.java.Log;

@Service
@Log
public class FileService {
	
	private static final String filePath = "D:/JavaProject/foreigners/";

    public Map<String, Object> uploadFile(String oriname, byte[] fileData) throws Exception {
    	Map<String, Object> result = new HashMap<>();
    	
        UUID uuid = UUID.randomUUID();
        String extention = oriname.substring(oriname.lastIndexOf("."));
        String savedfileName = uuid.toString() + extention;
        String filefullUrl = filePath + "/" + savedfileName;
        FileOutputStream fos1 = new FileOutputStream(filefullUrl);
        fos1.write(fileData);
        fos1.close();
        
        result.put("ori_file_name", oriname);
        result.put("url", filefullUrl);
        result.put("file_name", savedfileName);
        return result;
    }

    public void deleteFile(String filePath1) throws Exception {
        File deleteFile1 = new File(filePath1);
        if (deleteFile1.exists()) {
            deleteFile1.delete();
            log.info("파일을 삭제하였습니다.");
        } else {
            log.info("파일이 존재하지 않습니다.");
        }
    }

    public void downloadFile(String fileUrl, String oriname, HttpServletResponse response) {
    	String contentType = "";
    	String extention = fileUrl.substring(fileUrl.lastIndexOf(".") + 1).toLowerCase();
    	
    	if (extention.equals("pdf")) contentType = "application/pdf";
    	else if (extention.equals("docs")) contentType = "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
    	else if (extention.equals("hwp")) contentType = "application/x-hwp";
    	else contentType = "application/octet-stream";
       

        File file = new File(fileUrl);
        Long fileLength = file.length();

            response.setHeader("Content-Disposition", "attachment; filename=\"" + oriname + "\";");
            response.setHeader("Content-Transfer-Encoding", "binary");
            response.setHeader("Content-Type", contentType);
            response.setHeader("Content-Length", "" + fileLength);
            response.setHeader("Pragma", "no-cache;");
            response.setHeader("Expires", "-1;");

            try(
                        FileInputStream fis = new FileInputStream(fileUrl);
                        OutputStream out = response.getOutputStream();
                ){
                        int readCount = 0;
                        byte[] buffer = new byte[1024];
                    while((readCount = fis.read(buffer)) != -1){
                            out.write(buffer,0,readCount);
                    }
                }catch(Exception ex){
                    throw new RuntimeException("file Save Error");
                }
    }
}