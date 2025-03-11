package com.fptaptech.demo2.utils;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import java.util.Date;

public class JWTUtils {
    private static final String SECRET_KEY = "MY_SUPER_SECRET_KEY";
    private static final long EXPIRATION_TIME = 10 * 60 * 1000;

    public static String generateToken(String username, String role) {
        return JWT.create()
                .withSubject(username)
                .withClaim("role", role)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(Algorithm.HMAC256(SECRET_KEY));
    }
    public static DecodedJWT verifyToken(String token) throws Exception {
        try {
            return JWT.require(Algorithm.HMAC256(SECRET_KEY)).build().verify(token);
        } catch (Exception e) {
            throw new Exception("Invalid or expired token", e);
        }
    }
}
