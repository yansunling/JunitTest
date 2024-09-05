package com.encryption;

import cn.hutool.core.codec.Base62;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;


public class JwtTokenDecode {
    public static void main(String[] args) throws Exception{

        String token="Xn6kamhvdw5qQpxysOTPojH3j5DJVROCdOjWYtkm4XcvPuR0UrXutwieLNVB5lo8AcmHzFOD1mQOzAzA3TUQuIbryDuBc9EqDF0Cy4CkhehbFXdorV0wvGUk14qea4q3ABgiLMGHXXzd975MvLc6qiAFNe3jhJgTL8Bbhf6RP1GKozOqwEN8mokvynn2M2AHy4mJr9yYdGoMUCuFTxkyLmQtXvjDLFZffTyLURH1yRKbNptjx7vbjI5HrQNpmu6yfNZ71dttrlltjhhMli4XV9abT5LhPAc9GC14CT7VjtebpU7Q7nz31gmHofLf3nxkzu0jpV4ezfNMsVNtl48Sha1kbbPOOqA77OhoahnVUYVaSzOniYBWM0s4LWsMQXV4tI0x1zdkr64uOyc3mHlexgECCKKpMHrHDtpCdSo94rYj6yRN0giYUjsDWUoTAfzTXgMbCqCthxiYNm6EoGseabbO2NutxwQLleo5qmFdudVvR5ta2qyMUSTCp9M8f9FDw0G0KGyU2GJVckNKzs8kbFs31WlNZB2kPkaDRWzPR8OmKPbxwiAvzPNJoMzcuM7HyaCK9pTHf6vsRZBhhi7V3fZOKH6hoUXKBQha9m9dx02hBwPjeHUQGGJagQKcfdtsIMDlKk2agxSYmwnbLpEW77Lqj";
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
