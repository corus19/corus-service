package com.coronatracker.security;

import com.coronatracker.exceptions.InvalidJwtTokenException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Created by Sampath Katari on 26/03/20.
 */

@Service
public class SessionTokenProvider {

    private static final Logger logger = LoggerFactory.getLogger(SessionTokenProvider.class);

    @Value("${jwt.exp-time.days:1}")
    private int expTimeInDays;
    @Value("${jwt.exp-time.minutes:0}")
    private int expTimeInMinutes;
    @Value("${jwt.key}")
    private String secret;
    @Value("${jwt.issuer}")
    private String issuer;

    public String createToken(final String userId) {
        final LocalDateTime tokenExpireTime = LocalDateTime.now().plusDays(expTimeInDays).plusMinutes(expTimeInMinutes);
        final Date jwtExpireDate = Date.from(tokenExpireTime.toInstant(ZoneOffset.UTC));
        return Jwts.builder()
                .setSubject(userId)
                .setIssuedAt(new Date())
                .setExpiration(jwtExpireDate)
                .setIssuer(issuer)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    public String getUserIdFromToken(String token) {
        final Claims claims = Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    public boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(authToken);
            return true;
        } catch (MalformedJwtException ex) {
            logger.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            logger.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            logger.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            logger.error("JWT claims string is empty.");
        }
        throw new InvalidJwtTokenException("server.authentication.fail");
    }

}

