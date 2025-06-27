package com.encryption;

import cn.hutool.core.codec.Base62;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;


public class JwtTokenDecode {
    public static void main(String[] args) throws Exception{

        String token="eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJ1c2VySWQtY2xhaW0iOiIxODA3MjI5ODc5MSIsInJvbGVOYW1lLWNsYWltIjoiMTExMSIsIm9yZ0lkLWNsYWltIjoiIiwicm9sZUlkLWNsYWltIjoiMTExMSIsImJvb2wtY2xhaW0iOnRydWUsImRhdGUtY2xhaW0iOjE3NTA2NjM2NjcsIm51bWJlci1jbGFpbSI6NTcxLCJvcmdOYW1lLWNsYWltIjoiMTExMSIsInVzZXJOYW1lLWNsYWltIjoiIn0.Ixl3H7ISF8EkCePKZ9u7W0r_vZXC1tTLzQfbmDdz_qWE8E2nTkqQlrDydvUJhrlYd_MQDJ4rK-OFkZ91o2HtNg";
        //token="eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJ1c2VySWQtY2xhaW0iOiJUMTExMyIsImNvbXBOYW1lLWNsYWltIjoi6am86b6Z54mp5rWBIiwiY29tcEFsaWFzLWNsYWltIjoi6am86b6ZIiwicm9sZU5hbWUtY2xhaW0iOiIxMTExIiwib3JnSWQtY2xhaW0iOiIzNTAxMDExMzAyIiwicm9sZUlkLWNsYWltIjoiMTExMSIsImJvb2wtY2xhaW0iOnRydWUsImRhdGUtY2xhaW0iOiIyMDI1LTA1LTA2IDA4OjU2OjU4IiwibnVtYmVyLWNsYWltIjo1NzEsIm9yZ05hbWUtY2xhaW0iOiLln7rnoYDnoJTlj5Hpg6giLCJ1c2VyTmFtZS1jbGFpbSI6IuminOWtmeS7pCIsImNvbXBJZC1jbGFpbSI6InRsd2wifQ.8rWBhiDhjOHAj4PcDPdaOcRMXxO06AXMIPrIat65M59etkTWTVwIoxUfoCKUrPjQwVsylCQvCmhloncRHnwL4w";
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
