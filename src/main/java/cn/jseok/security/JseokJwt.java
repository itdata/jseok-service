package cn.jseok.security;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.nimbusds.jose.util.Base64URL;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.InvalidKeyException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.MacAlgorithm;
import io.jsonwebtoken.security.SecureDigestAlgorithm;
import org.springframework.http.HttpStatus;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class JseokJwt {

    private static final String SECRET_KEY = "ZfjuhAA2+GdESTm5LOQVtcRYdWoK3LFCqmPJ5qoJnHw=";

    private static final SecureDigestAlgorithm<SecretKey, SecretKey> ALGORITHM = Jwts.SIG.HS256;
    //private static final MacAlgorithm ALGORITHM = Jwts.SIG.HS256;

    private static final SecretKey KEY = Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));

    private static final long EXPIRATION = 1000 * 24 * 60 * 60L;

    /*
     * iss 签发者
     * sub 主题 可以是用户名
     * aud 接收者 可以是用户ID
     * exp 过期时间
     * nbf 生效时间 在此之前都不可用
     * iat 签发时间
     * jti JWT 的唯一标识
     * */
    public static String secretJwt(Map<String, String> map) {
        String jwt = null;
        try {
            JwtBuilder jwtBuilder = Jwts.builder();
            long var = new Date().getTime()+5000;
            System.out.println(var);
            jwt = jwtBuilder.header().add("typ", "jwt").keyId("jseok").and().issuer("cn.jseok")
                    //.subject("Bob")
                    .claims(map)
                    //.audience().add("you").add("me").and()

                    .expiration(new Date(var)) //a java.util.Date

                    //.notBefore(new Date(System.currentTimeMillis()+566565)) //a java.util.Date
                    .issuedAt(new Date()) // for example, now
                    .id("56556") //just an example id
                    .signWith(KEY, ALGORITHM).compact();
            System.out.println(jwt);
        } catch (InvalidKeyException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
        return jwt;
    }


    public static Map<String, String> parseToken(String jwt) {
//        content = Jwts.parser().verifyWith(key).build().parseSignedContent(jws).getPayload();
//        JwtParser build = parser.build();
        Map<String, String> jwtMap = new HashMap<>();
        try {
            JwtParserBuilder parser = Jwts.parser().verifyWith(KEY);
            Jws<Claims> claimsJws = parser.build().parseSignedClaims(jwt);
            JwsHeader header = claimsJws.getHeader();
            Set<Map.Entry<String, Object>> headers = header.entrySet();
            Map<String, String> headeMap = headers.stream().collect(HashMap::new, (map, entry) -> map.put(entry.getKey(), entry.getValue().toString()), Map::putAll);
            System.out.println(headeMap);
            Claims payload = claimsJws.getPayload();
            Set<Map.Entry<String, Object>> payloads = payload.entrySet();
            Map<String, String> payloadMap = payloads.stream().collect(HashMap::new, (map, entry) -> map.put(entry.getKey(), entry.getValue().toString()), Map::putAll);
            Stream<Map.Entry<String, String>> stream = Stream.concat(headeMap.entrySet().stream(), payloadMap.entrySet().stream());
            jwtMap = stream.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (map1, map2) -> map2));
            Base64URL encode = Base64URL.encode(claimsJws.getDigest());
            if (payloadMap.containsKey("exp")) {
                long extTime = Long.parseLong(jwtMap.get("exp"));
                long curTime = (new Date().getTime()/1000);
                // 过期了
                jwtMap.put("state", JwtState.VALID.toString());
                if (curTime > extTime) {
                    jwtMap.clear();
                    jwtMap.put("state", JwtState.EXPIRED.toString());
                    return jwtMap;
                }
                return jwtMap;
            } else {
                jwtMap.put("state", JwtState.INVALID.toString());
                return jwtMap;
            }

        } catch (JwtException | IllegalArgumentException e) {

            jwtMap.put("state", JwtState.INVALID.toString());

            return jwtMap;


        }
    }

    public static void main(String[] args) {
//        System.out.println(JwtState.VALID);
//
//        SecureRandom secureRandom = new SecureRandom();
//        byte[] bytes = new byte[32];
//        secureRandom.nextBytes(bytes);
//        String key = Base64.getEncoder().encodeToString(bytes);
//        System.out.println(key);

        Map<String, String> map = new HashMap<>();
        map.put("username", "root");
        map.put("userid", "28302");
        String jwt = secretJwt(map);
        Map<String, String> map1 = new HashMap<>();
        map1 = parseToken(jwt);
        System.out.println(map1 + "7*****89");

    }
}
