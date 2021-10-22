//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.decisionbox.utils;

import com.decisionbox.beans.ContentResponse;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.apache.http.entity.mime.content.FileBody;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

public class ProcessAttachmentPOST {
    private static RestTemplate restTemplate = new RestTemplate();

    public ProcessAttachmentPOST() {
    }

    public static String postMultiPartRequest(String urlString, File file, String fileName, String taskId, String processInstanceId, ObjectMapper obj, String pdfPath, String authorizationHeader) throws Exception {
        new FileBody(file);
        Path pathobj = Paths.get(pdfPath);
        byte[] pdfbytes = Files.readAllBytes(pathobj);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        headers.add("Authorization", authorizationHeader);
        MultiValueMap<String, String> fileMap = new LinkedMultiValueMap();
        ContentDisposition contentDisposition = ContentDisposition.builder("form-data").name("file").filename(fileName).build();
        fileMap.add("Content-Disposition", contentDisposition.toString());
        fileMap.add("Content-Type", "application/pdf");
        HttpEntity<byte[]> fileEntity = new HttpEntity(pdfbytes, fileMap);
        MultiValueMap<String, Object> body = new LinkedMultiValueMap();
        body.add("file", fileEntity);
        HttpEntity requestEntity = new HttpEntity(body, headers);

        try {
            ResponseEntity<ContentResponse> response = restTemplate.exchange(urlString, HttpMethod.POST, requestEntity, ContentResponse.class, new Object[0]);
            String contentId = ((ContentResponse)response.getBody()).getId();
            return contentId;
        } catch (NullPointerException | HttpClientErrorException var19) {
            var19.printStackTrace();
            ResponseEntity<ContentResponse> response = restTemplate.postForEntity(urlString, requestEntity, ContentResponse.class, new Object[0]);
            System.out.println(response);
            return null;
        }
    }
}
