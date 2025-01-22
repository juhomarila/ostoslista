package fi.ruoka.ostoslista.util;

import java.util.Date;

import org.springframework.core.env.Environment;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JwtUtil {

    private final Environment env;

    public JwtUtil(Environment env) {
        this.env = env;
    }

    private static final long EXPIRATION_TIME = 1000 * 60 * 60 * 24 * 365;

    public String generateToken(String username) {
        System.out.println("SIIKRITTI: " + env.getProperty("jwt.secret"));
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS256, env.getProperty("jwt.secret"))
                .compact();
    }

    public boolean validateToken(String token, String username) {
        String extractedUsername = extractUsername(token);
        return (username.equals(extractedUsername) && !isTokenExpired(token));
    }

    public String extractUsername(String token) {
        return extractClaims(token).getSubject();
    }

    private boolean isTokenExpired(String token) {
        return extractClaims(token).getExpiration().before(new Date());
    }

    private Claims extractClaims(String token) {
        System.out.println("TOOKKENI: " + token);
        return Jwts.parser()
                .setSigningKey(env.getProperty("jwt.secret"))
                .parseClaimsJws(token)
                .getBody();
    }
}
