package com.encryption;

import cn.hutool.core.codec.Base62;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;


public class JwtTokenDecode {
    public static void main(String[] args) throws Exception{

        String token="AY86o7d9Og5jVmj4roSRT5d8JrFMSkd0apvdO3BWZGfTXYvrmZm0L5snD7Mh3ef60LbZ1NImrQRwHE4tjjjCxC99fSYqJ0JmnDh5bui7VVbR64iwESfkyzbgpN8iKLSk1LKjuTMWoMCoUxo9VdbePDJLDWc7Wad1EbP9qAE1JFNedR06pysWb5x6EX6cskQtxRJT9Y2RbHfDfg65cu6prazdZKwzWHZalaRxZM5ETcq37ucLSddETa8k0o3LZ4lSfzPmsQR0PXg06H1iJtcmFR6LPY6k9meUg1TjeGWacP1wWzBBQUrX3BxG6aEmyrW1kmzRAS7GvwzXpu7RzhUs9lkjLN5Jv4E4V6gncODwam9Vp8ODSfTN27dVvRzyOEMPdGXyFWJcBJ98m6kjLDT9NH3r7F8dyH7ctWhI39cqJFfRhsGmRB656idbKaGRn3rm3wDC1raxguT7IvXLYWbpfe6SfjCvSawFQGfM8lyKOhmhB6dIdpQJj1e4OjmWVLFMz8cbB4xNQmqRlTFVMkriQdcpy0i208hGF77SpdjJcWY8tiXT25FvbAdQL1pAwtHTWgppBr7B3WPWzTKHwkFxE7PoN8cs1gB1sNQL5XQppA6R3d0pPQs32GWKb0f8DpCm01jh7gccXoI6iK31vzFGKfo3eiqFDBfV";
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
