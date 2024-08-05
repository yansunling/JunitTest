package com.encryption;

import cn.hutool.core.codec.Base62;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;


public class JwtTokenDecode {
    public static void main(String[] args) throws Exception{

        String token="QlBA0m3kscYcCIKq01yLnd0PS6P8gqA8d3pNlW8ZqpBEdyZHyLJQgtCgRLK6SGog9nnz9dCLS4NPc4XegVd17jNKBi2saQY1Bw82L7fryFhEgbvjaXW5k0NfkvLcydEgbd980yRr4EQL8DW79AqDAbJwkXmkQyM2zRZuiqkuNiVzHRcNvqcAjCf3ytRKYUt30XIXvN5UqVxmKevsnJCLe2xtl82nfys0JZ53xvdWn3F1EbQFxhC9pe8yhHUyuzNvVDunhYIjM5TxeOoQcsyEXrGwWXBGWmJ1FGXNZ3ihgsTAPBzhJoNmiY2E0XrTw4UHeCjGvsmK8dYb8Lxch4EHjtaBkVa4YT5Z";
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
