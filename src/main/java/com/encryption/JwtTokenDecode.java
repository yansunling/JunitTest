package com.encryption;

import cn.hutool.core.codec.Base62;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;


public class JwtTokenDecode {
    public static void main(String[] args) throws Exception{

        String token="eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJ1c2VySWQtY2xhaW0iOiJUMDI5MCIsImNvbXBOYW1lLWNsYWltIjoi6am86b6Z54mp5rWBIiwiY29tcEFsaWFzLWNsYWltIjoi6am86b6ZIiwicm9sZU5hbWUtY2xhaW0iOiIxMTExIiwib3JnSWQtY2xhaW0iOiIzNTAxMDEwNjAzMDEwMSIsInJvbGVJZC1jbGFpbSI6IjExMTEiLCJib29sLWNsYWltIjp0cnVlLCJkYXRlLWNsYWltIjoiMjAyNS0wMy0yNiAwNjozNTowNiIsIm51bWJlci1jbGFpbSI6NTcxLCJvcmdOYW1lLWNsYWltIjoi6LS15bee6ZOB6Lev5L2c5Lia5LiA57uEIiwidXNlck5hbWUtY2xhaW0iOiLmnY7lupTlh6QiLCJjb21wSWQtY2xhaW0iOiJ0bHdsIn0.r3m_X3QC0CEOhEM0LUAkDjRMamAXE-6k7hYl4DubqrsDicIUDfwSxUTrcRmpN5xovUReiR95fuqwq1zZsqGIQg";
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
