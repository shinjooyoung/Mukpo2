package com.sts.refund.web;

import java.io.OutputStreamWriter;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.io.BufferedReader;

/**
 * 스크랩 API 호출 클래스
 */
@Component
public class ScrapApi {

    private final String SCRAP_URL = "https://codetest.3o3.co.kr/v1/scrap";

    /**
     * 스크랩 데이터 요청
     * @param name 
     * @param regNo
     * @return
     * @throws Exception
     */
    public Map<String, Object> requestScrap(String name, String regNo) throws Exception  {

        Map<String, Object> body = new HashMap<>();
        body.put("name",name);
        body.put("regNo", regNo);

        String envUrl = "";
        String key = "";

        URL url = new URL(SCRAP_URL);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setRequestMethod("POST");
        conn.setRequestProperty("Accept", "application/json");
        conn.setRequestProperty("Content-type", "application/json;charset=UTF-8");
        conn.setDoInput(true);
        conn.setDoOutput(true);

        ObjectMapper mapper = new ObjectMapper();
        String bodyStr = mapper.writeValueAsString(body);

        OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
        wr.write(bodyStr);
        wr.flush();

        StringBuilder sb = new StringBuilder();
        BufferedReader br;
        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
        } else {
            br = new BufferedReader(new InputStreamReader(conn.getErrorStream(), "UTF-8"));
        }

        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        br.close();

        Map<String, Object> result = new HashMap<>();

        return mapper.readValue(sb.toString(), new TypeReference<Map<String, Object>>() {});
    }
}
