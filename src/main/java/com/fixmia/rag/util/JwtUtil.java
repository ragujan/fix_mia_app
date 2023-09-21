package com.fixmia.rag.util;

import com.fixmia.rag.dtos.UserDTO;
import com.fixmia.rag.services.JTIService;
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
    private static final Long EXPIRATION_TIME = 30L;
    private static final Long REFRESH_TOKEN_LIFE = 10080L;
    private static final String TYPE = "user-type";
    private static String SECRET = "";
    static {
        Dotenv dotenv = Dotenv.configure().ignoreIfMalformed().load();
        SECRET = dotenv.get("PRIVATE_SECRET_JWT_KEY");
    }
    public String generateJWTToken(Map<String,String> claims, String subject, Long expiration){
        Signer signer = HMACSigner.newSHA512Signer(SECRET);
        JWT jwt = new JWT()
                .setExpiration(ZonedDateTime.now(ZoneOffset.UTC).plusMinutes(EXPIRATION_TIME))
                .setIssuedAt(ZonedDateTime.now(ZoneOffset.UTC))
                .setIssuer(ISSUER)
                .setSubject(subject);
        claims.keySet().forEach(k->{
            if(claims.get(k) != null){
                jwt.addClaim(k,claims.get(k));
            }
        });
        String jti = JTIService.generateJTI();
        jwt.addClaim("jti", jti);
        return JWT.getEncoder().encode(jwt,signer);
    }
    public  String generateAccessToken(UserDTO user){
        Map<String,String> claims = new HashMap<>();
        claims.put(CLAIM_KEY_USERNAME,user.getEmail());
        claims.put(CLAIM_KEY_CREATED,new Date().toString());
        return generateJWTToken(claims,user.getEmail(),EXPIRATION_TIME);

    }
    public Map<String,String> getClaimsFromToken(String token){
        Verifier verifier = HMACVerifier.newVerifier(SECRET);
        JWT jwt = JWT.getDecoder().decode(token,verifier);
        Map<String,Object> jwtAllClaims = jwt.getAllClaims();
        Map<String,String> claims = new HashMap<>();

        if(jwt != null){
            jwtAllClaims.forEach((k,v)->{
                claims.put(k,v.toString());
            });
        }
        return claims;
    }
    public Date getExpiredDateFromToken(String token){
        Verifier verifier = HMACVerifier.newVerifier(SECRET);
        JWT jwt = JWT.getDecoder().decode(token,verifier);
        return new Date(jwt.expiration.toInstant().toEpochMilli());
    }
    public Long getExpirationTimeInSeconds(String token){
        Date expiryDate = getExpiredDateFromToken(token);
        Date currentDate = new Date();
        return (expiryDate.getTime()-currentDate.getTime())/1000;
    }
    public boolean isTokenExpired(String token){
        Date expiryDate  = getExpiredDateFromToken(token);
        return expiryDate.before(new Date(System.currentTimeMillis()));
    }
    public String getUserEmailFromToken(String token){
        Map<String,String> claims = getClaimsFromToken(token);
        return claims.get(CLAIM_KEY_USERNAME);
    }
    public boolean isJTIValid (String token){

        Map<String,String> claims = getClaimsFromToken(token);
        String jti = claims.get("jti");
        System.out.println("jti is "+jti);
        boolean jtiExistsOnDb = JTIService.isJTIExists(jti);
        return jtiExistsOnDb;
    }
    public boolean validateToken(String token,UserDTO userDTO){
        String userEmail = getUserEmailFromToken(token);
        boolean userEmailExists = userEmail.equals(userDTO.getEmail());
        boolean tokenExpired = isTokenExpired(token);
        boolean jtiExistsOnDb = isJTIValid(token);

        return userEmailExists && !tokenExpired && jtiExistsOnDb;
    }
    public String generateRefreshToken(UserDTO userDTO){
        Map<String,String> claims = new HashMap<>();
        claims.put(CLAIM_KEY_USERNAME,userDTO.getEmail());
        claims.put(CLAIM_KEY_CREATED,new Date().toString());
        return generateJWTToken(claims,userDTO.getEmail(),REFRESH_TOKEN_LIFE);
    }
    public static void main2(String[] args) {
        UserDTO user = new UserDTO();
        user.setEmail("brobash@gmail.com");
        JwtUtil jwtUtil = new JwtUtil();
        String token = jwtUtil.generateAccessToken(user);
        System.out.println("token");
        System.out.println(token);

        token = "eyJhbGciOiJIUzUxMiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE2OTUyNTUzMzksImlhdCI6MTY5NTI1MzUzOSwiaXNzIjoiZml4X21pYSIsInN1YiI6ImJyb2Jhc2hAZ21haWwuY29tIiwianRpIjoiZWUxcnA3QmZzQyIsImNyZWF0ZWQiOiJUaHUgU2VwIDIxIDA1OjE1OjM5IElTVCAyMDIzIn0.C0EqaiNSc3Dxb-O6K_IsRGObAaM96Oc88BfVQzuZ9y3-4w_P51sQsf4CbDlnCmzL_3Ppo7u5WM2l3DKX62dW4Q";
        String jti = "ee1rp7BfsC";
        Map<String,String> claims = jwtUtil.getClaimsFromToken(token);
        claims.forEach((k,v)->{
            System.out.println(claims.get(k));
        });
        System.out.println("Expiration is on ");
        System.out.println(jwtUtil.getExpiredDateFromToken(token).toString());
        jwtUtil.isJTIValid(token);
        System.out.println("Is token valid ?");
        System.out.println(jwtUtil.validateToken(token,user));


    }

}
