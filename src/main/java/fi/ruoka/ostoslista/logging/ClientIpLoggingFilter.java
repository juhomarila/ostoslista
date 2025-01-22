package fi.ruoka.ostoslista.logging;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class ClientIpLoggingFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(ClientIpLoggingFilter.class);

    @Override
    protected void doFilterInternal(@SuppressWarnings("null") HttpServletRequest request,
            @SuppressWarnings("null") HttpServletResponse response, @SuppressWarnings("null") FilterChain filterChain)
            throws ServletException, IOException {
        String xforwardedForIp = getForwardedForIp(request);
        String clientIp = getXRealIp(request);
        logger.info("X-Forwarded-For IP: {}", xforwardedForIp);
        logger.info("X-Real-IP: {}", clientIp);

        filterChain.doFilter(request, response);
    }

    private String getForwardedForIp(HttpServletRequest request) {
        String xForwardedFor = request.getHeader("X-Forwarded-For");
        if (xForwardedFor != null && !xForwardedFor.isEmpty()) {
            return xForwardedFor.replaceAll(",\\s*", ", ").trim();
        }
        return request.getRemoteAddr();
    }

    private String getXRealIp(HttpServletRequest request) {
        String xRealIP = request.getHeader("X-Real-IP");
        if (xRealIP != null && !xRealIP.isEmpty()) {
            return xRealIP;
        }
        return request.getRemoteAddr();
    }
}
