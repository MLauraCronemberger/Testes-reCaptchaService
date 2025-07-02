package com.example.demo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class Teste {
	
    @GetMapping("/ping")
    public String ping() {
        return "pong";
    }

    @PostMapping("/echo")
    public String echo(@RequestBody String body) {
        return "Você enviou: " + body;
    }
    
    @PostMapping("/teste")
    private boolean verifyCaptcha(@RequestBody String token) throws IOException{
        String url = "https://www.google.com/recaptcha/api/siteverify";
        HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
        conn.setRequestMethod("POST");
        conn.setDoOutput(true);

        String postData = "secret=" + "6LeIxAcTAAAAAGG-vFI1TnRWxMZNFuojJ4WifJWe" + "&response=" + token;
        try (OutputStream os = conn.getOutputStream()) {
            os.write(postData.getBytes(StandardCharsets.UTF_8));
        }
        
        ObjectMapper mapper = new ObjectMapper();
        CaptchaResponse response = mapper.readValue(conn.getInputStream(), CaptchaResponse.class);
        
        // Leia uma única vez o InputStream
//        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
//        StringBuilder json = new StringBuilder();
//        String line;
//        while ((line = reader.readLine()) != null) {
//            json.append(line);
//        }
//        reader.close();
//
//        System.out.println("Resposta JSON: " + json);
//
//        // Aqui está o conserto
//        ObjectMapper mapper = new ObjectMapper();
//        CaptchaResponse response = mapper.readValue(json.toString(), CaptchaResponse.class);
        
        System.out.println("Response" + response);

        
        return true;
        
    }
    

}
