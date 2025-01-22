package fi.ruoka.ostoslista.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.Claims;

import java.util.Date;
import org.springframework.core.env.Environment;

public class JwtUtil {

    private Environment env;

    public JwtUtil(Environment env) {
        this.env = env;
    }

    private static final long EXPIRATION_TIME = 1000 * 60 * 60 * 24 * 365;

    public String generateToken(String username) {
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
        return Jwts.parser()
                .setSigningKey(env.getProperty("jwt.secret"))
                .parseClaimsJws(token)
                .getBody();
    }
}
