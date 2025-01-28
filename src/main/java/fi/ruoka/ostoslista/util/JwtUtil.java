package fi.ruoka.ostoslista.util;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JwtUtil {

    private final Environment env;

    public JwtUtil(Environment env) {
        this.env = env;
    }

    private static final Logger logger = LoggerFactory.getLogger(JwtUtil.class);

    private static final long EXPIRATION_TIME = 1000L * 60 * 15; // 15 minutes

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
        try {
            return extractClaims(token).getSubject();
        } catch (Exception e) {
            return null;
        }
    }

    public String generateRefreshToken(String username) {
        long expirationDuration = 1000L * 60 * 60 * 24 * 31;
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationDuration)) // 31 days
                .signWith(SignatureAlgorithm.HS256, env.getProperty("jwt.secret"))
                .compact();
    }

    public boolean validateRefreshToken(String token) {
        try {
            extractClaims(token);
            return true;
        } catch (Exception e) {
            logger.error("Error validating refresh token: " + e.getMessage());
            return false;
        }
    }

    private boolean isTokenExpired(String token) {
        try {
            return extractClaims(token).getExpiration().before(new Date());
        } catch (Exception e) {
            return true;
        }
    }

    private Claims extractClaims(String token) throws Exception {
        try {
            return Jwts.parser()
                    .setSigningKey(env.getProperty("jwt.secret"))
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            throw new Exception(e.getMessage());
        }

    }
}
