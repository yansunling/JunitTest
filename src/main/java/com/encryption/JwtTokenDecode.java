package com.encryption;

import cn.hutool.core.codec.Base62;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;


public class JwtTokenDecode {
    public static void main(String[] args) throws Exception{

        String token="eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJ1c2VySWQtY2xhaW0iOiJUMTExMyIsInJvbGVOYW1lLWNsYWltIjoiMTExMSIsIm9yZ0lkLWNsYWltIjoiMzUwMTAxMTMwMiIsInJvbGVJZC1jbGFpbSI6IjExMTEiLCJib29sLWNsYWltIjp0cnVlLCJkYXRlLWNsYWltIjoiMjAyNC0wNi0yNiAwOTo1MDowMSIsIm51bWJlci1jbGFpbSI6NTcxLCJvcmdOYW1lLWNsYWltIjoi5Z-656GA56CU5Y-R6YOoIiwidXNlck5hbWUtY2xhaW0iOiLpopzlrZnku6QifQ.EMRF7-u_YLBVU4IPxOxDzz1tFxrDAiwpO6jvWT-T1Xda91TcB-2AqaMoiqD9TxBve5YWhZFLVJhOAQcB8G0G-Q";
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
