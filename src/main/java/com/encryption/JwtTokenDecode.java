package com.encryption;

import cn.hutool.core.codec.Base62;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;


public class JwtTokenDecode {
    public static void main(String[] args) throws Exception{

        String token="11lPfE315GPjVKwdGisDJvLWYsK2tO8BFX1qZPwm5kc7otYUaHIvo72eTLf6V2BQbkhbYuuM09q6wi2N1Ih5aTht2NgvHBhopOijv3QfmMF6nH3mrnKUniHp7kH1xO1a3A8YNjll2sTfumu0ob78A2PvY9H73mjF59OeWHFCtksIBFWiLuLkwolrdaxNgzvx0G6p5uneO7A7Ci9deUfHHYn1nz6IGyRF5bemYDoeKQh8zHaCzMjacp7CRL2ShPO8t9UD7RP8Ud5tyuG1V2OCD8eCcJbk7C9qsjnax8eIbZXKTZo6aLY3UFCbr5GRFfDNXRf3pA0RTINooVCt3sC5mULSvLIjdyq3kWBYDIQV4siqc8NiAo3rMP1ZOqo8qmQDP9Jkk65h7DMMZ27TyamFiVaJiRHydbSeQe72aMB5BxVNSbsgXZjFHJECXQxLY4dEut81fdN8zoARDw2SfPOUE91huG8MPu57HKHZWheDaCaqmixa4KPPKVVnx4hUbGrfUEk7zaGmCrr2VgRMJliw68I4zUe1hYI6c44GSLGlmo0YtwB91VNi9uaRjG17Gp5RWsH0a6S4c8ktYDMyhEwaOHjn3Fn0C1VOpIrrtEYx4Kh8SDlekyxeYImt3tcrfElvFFBFIGtbewMlGU3Gr11HPVnkOl2lfPm59KH19qBrTczd9L";
        DecodedJWT decodedJWT1 = null;
        JWTVerifier verifier1 = JWT.require(Algorithm.HMAC512("CALStvl6Sv+DKi7WnCRMOYNWAgceYyy")).build();
        try {
            decodedJWT1 = verifier1.verify(token);
        } catch (Exception e) {
            e.printStackTrace();
            String jwtToken = Base62.decodeStr(token);
            decodedJWT1 = verifier1.verify(jwtToken);
        }

        String userId = decodedJWT1.getClaim("userId-claim").asString();
        String userName = decodedJWT1.getClaim("userName-claim").asString();
        String orgId = decodedJWT1.getClaim("orgId-claim").asString();
        String compId = decodedJWT1.getClaim("compId-claim").asString();

        System.out.println("userId:"+userId+";userName:"+userName+";orgId:"+orgId+";compId:"+compId);

    }
}
