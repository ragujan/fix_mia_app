package com.fixmia.rag.util;

import io.fusionauth.jwt.Signer;
import io.fusionauth.jwt.Verifier;
import io.fusionauth.jwt.domain.JWT;
import io.fusionauth.jwt.hmac.HMACSigner;
import io.fusionauth.jwt.hmac.HMACVerifier;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;

public class JwtUtil {
    private static final String CLAIM_KEY_USERNAME = "sub";
    private static final String CLAIM_KEY_CREATED = "created";
    private static final String ISSUER = "fix_mia";
    private static final String SECRET = "111222333444";

    public static void main(String[] args) {
        Signer signer = HMACSigner.newSHA512Signer(SECRET);
        JWT jwt = new JWT()
                .setIssuer("www.fixmia.com")
                .setIssuedAt(ZonedDateTime.now(ZoneOffset.UTC))
                .setSubject("ragjn")
                .setExpiration(ZonedDateTime.now(ZoneOffset.UTC).plusMinutes(30));
        jwt.addClaim("email","marshall@gmail.com");
        String encodedJWT = JWT.getEncoder().encode(jwt,signer);

        System.out.println("jwt token is ");
        System.out.println(encodedJWT);

        Verifier verifier = HMACVerifier.newVerifier(SECRET);
        jwt = JWT.getDecoder().decode(encodedJWT,verifier);

        System.out.println(jwt.issuer);

    }

}
