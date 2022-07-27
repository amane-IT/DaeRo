package com.ssafy.daero.user.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.daero.util.CryptoUtil;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtService {
    private final String SECRET_KEY = "아이고_힘들다";
    private final Base64.Encoder encoder = Base64.getEncoder();
    private final Base64.Decoder decoder = Base64.getDecoder();

    public String create(int userSeq, String userEmail) {
        String header = "{\"alg\":\"HS256\",\"typ\":\"JWT\"}";
        String payload = String.format("{\"user_seq\":\"%s\",\"user_email\":\"%s\"}", userSeq, userEmail);
        byte[] encodedHeader = encoder.encode(header.getBytes());
        byte[] encodedPayload = encoder.encode(payload.getBytes());

        String headerString = new String(encodedHeader);
        String payloadString = new String(encodedPayload).substring(0, new String(encodedPayload).length() - 1);

        String signature = CryptoUtil.Sha512.hash(String.format("%s%s%s", headerString, payloadString, SECRET_KEY));
        return headerString + "." + payloadString + "." + signature;
    }

    public boolean isValid(String jwt) {
        String[] jwtSplitter = jwt.split("\\.");
        String headerString = jwtSplitter[0];
        String payloadString = jwtSplitter[1];
        String signature = jwtSplitter[2];
        //TODO: 시그니처 확인할때 DB에 액세스 -> 메서드 재활용해야됨. Merge 이후 작업하기.
        return signature.equals(CryptoUtil.Sha512.hash(String.format("%s%s%s", headerString, payloadString, SECRET_KEY)));
    }

    public Map<String, String> decodeJwt(String jwt) {
        if (!isValid(jwt)) { return null; }
        String[] jwtSplitter = jwt.split("\\.");
        String encodedPayload = jwtSplitter[1];
        int equalCount = 0;
        for(int i = encodedPayload.length()-1; i > encodedPayload.length() - 3; i--) {
            if(Character.toString(encodedPayload.charAt(i)).matches("=")) {
                equalCount ++;
            }
        }
        String realEncodePayload = encodedPayload.substring(0, encodedPayload.length()-equalCount);
        byte[] decodePayload = decoder.decode(realEncodePayload);
        String decodePayloadString = new String(decodePayload);
        ObjectMapper mapper = new ObjectMapper();
        Map<String, String> userInfo = new HashMap<>();
        try{
            userInfo = mapper.readValue(decodePayloadString, Map.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return userInfo;
    }

}

