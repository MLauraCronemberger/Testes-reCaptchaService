package com.example.demo;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class CaptchaController {
	
	private final String SECRET_KEY = "6LeIxAcTAAAAAGG-vFI1TnRWxMZNFuojJ4WifJWe";
	
//  essa secretKey é uma de teste do Google
//  
//  token de teste tb: "10000000-aaaa-bbbb-cccc-000000000001"
	
    @GetMapping("/ping")
    public String ping() {
        return "pong";
    }
    
    @PostMapping("/ex1")
    private boolean verifyCaptcha(@RequestBody String token) throws IOException{
        String url = "https://www.google.com/recaptcha/api/siteverify";
        HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
        conn.setRequestMethod("POST");
        conn.setDoOutput(true);

        String postData = "secret=" + SECRET_KEY + "&response=" + token;
        try (OutputStream os = conn.getOutputStream()) {
            os.write(postData.getBytes(StandardCharsets.UTF_8));
        }
        
        ObjectMapper mapper = new ObjectMapper();
        CaptchaResponse response = mapper.readValue(conn.getInputStream(), CaptchaResponse.class);
        
        System.out.println("Response" + response);

        
        return true;
        
    }
    
    @PostMapping("/ex2")
    public String catpcha(@RequestBody CaptchaRequest captchaRequest) {

        System.out.println("token: " + captchaRequest.token);

        String request = "https://www.google.com/recaptcha/api/siteverify?"
                + "secret=" + SECRET_KEY
                + "&response=" + captchaRequest.token ;

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> respostaApi = restTemplate.getForEntity(request, String.class);

        System.out.println("request: " + request);
        System.out.println("respostaApi getBody: \n" + respostaApi.getBody());

        return respostaApi.getBody();
    }

    

}
