package com.ssafy.daero.user.service;

import com.ssafy.daero.user.mapper.JwtMapper;
import com.ssafy.daero.common.util.CryptoUtil;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtService {
    private final String SECRET_KEY = "아이고_힘들다";
    private final Base64.Encoder encoder = Base64.getEncoder();
    private final Base64.Decoder decoder = Base64.getDecoder();

    private final JwtMapper jwtMapper;

    public JwtService(JwtMapper jwtMapper) {
        this.jwtMapper = jwtMapper;
    }

    public String create(int userSeq, String userEmail) {
        String header = "{\"alg\":\"HS256\",\"typ\":\"JWT\"}";
        String payload = String.format("{\"user_seq\":\"%s\",\"user_email\":\"%s\"}", userSeq, userEmail);
        byte[] encodedHeader = encoder.encode(header.getBytes());
        byte[] encodedPayload = encoder.encode(payload.getBytes());

        String headerString = new String(encodedHeader);
        String payloadString = new String(encodedPayload);

        String signature = CryptoUtil.Sha256.hash(String.format("%s%s%s", headerString, payloadString, SECRET_KEY));
        return headerString + "." + payloadString + "." + signature;
    }

    public boolean isValid(String jwt) {
        String[] jwtSplitter = jwt.split("\\.");
        String headerString = jwtSplitter[0];
        String payloadString = jwtSplitter[1];
        String signature = jwtSplitter[2];
        if (!signature.equals(CryptoUtil.Sha256.hash(String.format("%s%s%s", headerString, payloadString, SECRET_KEY)))) {
            return false;
        }
        String decodePayload = new String(decoder.decode(payloadString));
        String[] payloadData = decodePayload.substring(2, decodePayload.length() - 2).split("\"[,:]\"");
        String userSeqStr = payloadData[1];
        String userEmail = payloadData[3];
        try {
            return this.jwtMapper.selectUserEmailByUserSeq(Integer.parseInt(userSeqStr)).equals(userEmail);
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public Map<String, String> decodeJwt(String jwt) {
        String[] jwtSplitter = jwt.split("\\.");
        String payloadString = jwtSplitter[1];
        String decodePayload = new String(decoder.decode(payloadString));
        String[] payloadData = decodePayload.substring(2, decodePayload.length() - 2).split("\"[,:]\"");
        String userSeq = payloadData[1];
        String userEmail = payloadData[3];

        Map<String, String> userInfo = new HashMap<>();
        userInfo.put("user_seq", userSeq);
        userInfo.put("user_email", userEmail);
        return userInfo;
    }

    public int getUserSeq(String jwt) {
        return Integer.parseInt(decodeJwt(jwt).get("user_seq"));
    }

    public String getUserEmail(String jwt) {
        return decodeJwt(jwt).get("user_email");
    }
}

