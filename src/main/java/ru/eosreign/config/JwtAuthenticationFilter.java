package ru.eosreign.config;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;

import org.springframework.web.filter.OncePerRequestFilter;
import ru.eosreign.service.UserService;
import ru.eosreign.util.JwtService;

import java.io.IOException;

@Component
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    public static final String BEARER_PREFIX = "Bearer ";
    public static final String HEADER_NAME = "Authorization";
    public static final String EXCEPTION_LOG = "JWT Authorization error: {}";
    public static final String F_EXCEPTION_JSON = "\"{\"message\": \"%s\"}\"";
    public static final String CONTENT_TYPE = "application/json";

    @Qualifier("userService")
    private final UserService userService;
    private final JwtService jwtService;

    public JwtAuthenticationFilter(JwtService jwtService,
                                   UserService userService) {
        this.jwtService = jwtService;
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws IOException {
        try {
            // Получаем токен из заголовка
            var authHeader = request.getHeader(HEADER_NAME);
            if (StringUtils.isEmpty(authHeader) || !StringUtils.startsWith(authHeader, BEARER_PREFIX)) {
                filterChain.doFilter(request, response);
                return;
            }
            // Обрезаем префикс и получаем имя пользователя из токена
            var jwt = authHeader.substring(BEARER_PREFIX.length());
            var username = jwtService.extractUsername(jwt);

            if (StringUtils.isNotEmpty(username) && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userService
                        .userDetailsService()
                        .loadUserByUsername(username);

                // Если токен валиден, то аутентифицируем пользователя
                if (jwtService.isTokenValid(jwt, userDetails) && jwtService.isAccessToken(jwt)) {
                    SecurityContext context = SecurityContextHolder.createEmptyContext();

                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    context.setAuthentication(authToken);
                    SecurityContextHolder.setContext(context);
                }
            }
            filterChain.doFilter(request, response);

        } catch (MalformedJwtException e) {
            log.warn(EXCEPTION_LOG, e.getMessage());
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.setContentType(CONTENT_TYPE);
            String jsonResponse = "{\"message\": \"JwtToken is invalid\"}";
            response.getWriter().write(jsonResponse);
        } catch (ExpiredJwtException e) {
            log.warn(EXCEPTION_LOG, e.getMessage());
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType(CONTENT_TYPE);
            String jsonResponse = String.format(F_EXCEPTION_JSON, e.getMessage());
            response.getWriter().write(jsonResponse);
        } catch (JwtException e) {
            log.warn(EXCEPTION_LOG, e.getMessage());
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.setContentType(CONTENT_TYPE);
            String jsonResponse = String.format(F_EXCEPTION_JSON, e.getMessage());
            response.getWriter().write(jsonResponse);
        } catch (UsernameNotFoundException e) {
            log.warn(EXCEPTION_LOG, e.getMessage());
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType(CONTENT_TYPE + ";charset=UTF-8");
            String jsonResponse = String.format(F_EXCEPTION_JSON, e.getMessage());
            response.getWriter().write(jsonResponse);
        } catch (Exception e) {
            log.warn(EXCEPTION_LOG, e.getMessage());
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.setContentType(CONTENT_TYPE);
            String jsonResponse = String.format(F_EXCEPTION_JSON, e.getMessage());
            response.getWriter().write(jsonResponse);
        }
    }
}