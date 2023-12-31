package com.fixmia.rag.util;

import com.fixmia.rag.dtos.UserDTO;
import com.fixmia.rag.services.JTIService;
import com.fixmia.rag.util.hibernate.RowChecker;
import io.fusionauth.jwt.InvalidJWTException;
import io.fusionauth.jwt.JWTExpiredException;
import io.fusionauth.jwt.Signer;
import io.fusionauth.jwt.Verifier;
import io.fusionauth.jwt.domain.JWT;
import io.fusionauth.jwt.hmac.HMACSigner;
import io.fusionauth.jwt.hmac.HMACVerifier;
import io.github.cdimascio.dotenv.Dotenv;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtUtil {
    private static final String CLAIM_KEY_USERNAME = "sub";
    private static final String CLAIM_KEY_CREATED = "created";
    private static final String ISSUER = "fix_mia";
    private static final Long EXPIRATION_TIME = 3600L;
    private static final Long REFRESH_TOKEN_LIFE = 86400L;
    private static final String TYPE = "user-type";
    private static String SECRET = "";

    static {
        Dotenv dotenv = Dotenv.configure().ignoreIfMalformed().load();
        SECRET = dotenv.get("PRIVATE_SECRET_JWT_KEY");
    }

    public String generateJWTToken(Map<String, String> claims, String subject, Long expiration) {
        Signer signer = HMACSigner.newSHA512Signer(SECRET);
        JWT jwt = new JWT().setExpiration(ZonedDateTime.now(ZoneOffset.UTC).plusSeconds(expiration)).setIssuedAt(ZonedDateTime.now(ZoneOffset.UTC)).setIssuer(ISSUER).setSubject(subject);
        claims.keySet().forEach(k -> {
            if (claims.get(k) != null) {
                jwt.addClaim(k, claims.get(k));
            }
        });
//        generate a random string and insert that into the database
//        then use it as a jti for the jwt token
        String jti = JTIService.generateJTI();
        jwt.addClaim("jti", jti);
        return JWT.getEncoder().encode(jwt, signer);
    }

    public String generateAccessTokenForUser(UserDTO user) {
        Map<String, String> claims = new HashMap<>();
        claims.put(CLAIM_KEY_USERNAME, user.getEmail());
        claims.put(CLAIM_KEY_CREATED, new Date().toString());
        claims.put(TYPE, "user");
        return generateJWTToken(claims, user.getEmail(), EXPIRATION_TIME);

    }

    public JWT maybeToken(String token) {

        try {
            Verifier verifier = HMACVerifier.newVerifier(SECRET);
            JWT jwt = JWT.getDecoder().decode(token, verifier);
            return jwt;
        } catch (JWTExpiredException ex) {
            System.out.println("token is expired so ");
        } catch
        (InvalidJWTException ex) {
            System.out.println("this is not a json token at all");
        }
        return null;
    }

    public Map<String, String> getClaimsFromToken(String token) {
        Verifier verifier = HMACVerifier.newVerifier(SECRET);
        JWT jwt = JWT.getDecoder().decode(token, verifier);
        Map<String, Object> jwtAllClaims = jwt.getAllClaims();
        Map<String, String> claims = new HashMap<>();

        if (jwt != null) {
            jwtAllClaims.forEach((k, v) -> {
                claims.put(k, v.toString());
            });
        }
        return claims;
    }

    public Date getExpiredDateFromToken(String token) {
        Verifier verifier = HMACVerifier.newVerifier(SECRET);
        JWT jwt = JWT.getDecoder().decode(token, verifier);
        return new Date(jwt.expiration.toInstant().toEpochMilli());
    }

    public Long getExpirationTimeInSeconds(String token) {
        Date expiryDate = getExpiredDateFromToken(token);
        Date currentDate = new Date();
        return (expiryDate.getTime() - currentDate.getTime()) / 1000;
    }

    public String getJTIFromToken(String token) {
        Verifier verifier = HMACVerifier.newVerifier(SECRET);
        JWT jwt = JWT.getDecoder().decode(token, verifier);
        Map<String, Object> jwtAllClaims = jwt.getAllClaims();
        return String.valueOf(jwtAllClaims.get("jti"));

    }

    public boolean isTokenExpired(String token) {
        try {

            Date expiryDate = getExpiredDateFromToken(token);
            return expiryDate.before(new Date(System.currentTimeMillis()));
        } catch (JWTExpiredException ex) {
            return false;
        }
    }

    public String getUserEmailFromToken(String token) {
        Map<String, String> claims = getClaimsFromToken(token);
        return claims.get(CLAIM_KEY_USERNAME);
    }

    public boolean isJTIValid(String token) {
        if (maybeToken(token) == null) {
            return false;
        } else {
            Map<String, String> claims = getClaimsFromToken(token);
            String jti = claims.get("jti");
            System.out.println("jti is " + jti);
            boolean jtiExistsOnDb = JTIService.isJTIExists(jti);
            return jtiExistsOnDb;
        }
    }

    public boolean isTokenValid(String token) {
        String userEmail = getUserEmailFromToken(token);
        boolean userExists = RowChecker.rowExists("User", "email", userEmail);
        boolean tokenExpired = isTokenExpired(token);
        boolean jtiExistsOnDb = isJTIValid(token);

        return !tokenExpired && jtiExistsOnDb && userExists;
    }

    public String generateRefreshTokenForUser(UserDTO userDTO) {
        Map<String, String> claims = new HashMap<>();
        claims.put(CLAIM_KEY_USERNAME, userDTO.getEmail());
        claims.put(CLAIM_KEY_CREATED, new Date().toString());
        claims.put(TYPE, "user");
        return generateJWTToken(claims, userDTO.getEmail(), REFRESH_TOKEN_LIFE);
    }

}
