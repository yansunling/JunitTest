package com.encryption;

import cn.hutool.core.codec.Base62;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;


public class JwtTokenDecode {
    public static void main(String[] args) throws Exception{

        String token="eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJ1c2VySWQtY2xhaW0iOiJUMTA5MiIsInJvbGVOYW1lLWNsYWltIjoiMTExMSIsIm9yZ0lkLWNsYWltIjoiMzUwMTAxMTMwMyIsInJvbGVJZC1jbGFpbSI6IjExMTEiLCJib29sLWNsYWltIjp0cnVlLCJkYXRlLWNsYWltIjoiMjAyNC0wNy0xMSAwOTozNDowMyIsIm51bWJlci1jbGFpbSI6NTcxLCJvcmdOYW1lLWNsYWltIjoi5Lia5Yqh56CU5Y-R6YOoIiwidXNlck5hbWUtY2xhaW0iOiLmrKfpmLPmsZ_mu5QifQ.B82I7Rd1y2NzVQkddJ3TRsepm5GpKdxFe0C1BjzsgFDQd9oWCv5Dfs0X-q9FMHn3IbYLcPRNVHxDYgykv4usJw";
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
