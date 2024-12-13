package com.apajaro.platform.infrastructure.web.jwt;

import com.apajaro.platform.application.Logger;
import com.apajaro.platform.domain.entity.UserLogin;
import com.apajaro.platform.domain.valueobject.ID;
import io.jsonwebtoken.*;
import lombok.AllArgsConstructor;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.Set;

@AllArgsConstructor
public class JWTService {
    private String secret;

    private Integer expirationInSeconds;

    private Integer refreshExpirationInDays;

    private Logger logger;

    /**
     * Validates the jwt token and returns true if it is true for the following statements:
     * 1. The token is well signed.
     * 2. The token is not malformed.
     * 3. The token is not expired.
     * 4. The token is supported.
     * 5. The token is not empty.
     *
     * @param jwtToken
     * @return
     */
    public Boolean validateToken(String jwtToken, Boolean isRefreshToken) {
        try {
            Jws<Claims> claims = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(jwtToken);

            boolean containsIsRefreshKey = claims.getBody().containsKey("isRefresh");
            if (!isRefreshToken && containsIsRefreshKey) {
                return Boolean.FALSE;
            }

            return Boolean.TRUE;
        } catch (SignatureException ex) {
            logger.error("Invalid JWT signature", ex);
        } catch (MalformedJwtException ex) {
            logger.error("Invalid JWT token", ex);
        } catch (ExpiredJwtException ex) {
            logger.error("Expired JWT token", ex);
        } catch (UnsupportedJwtException ex) {
            logger.error("Unsupported JWT token", ex);
        } catch (IllegalArgumentException ex) {
            logger.error("JWT claims string is empty", ex);
        }

        return Boolean.FALSE;
    }

    public String getUserIdFromToken(String jwtToken) {
        Claims claims = Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(jwtToken)
                .getBody();

        return claims.getSubject();
    }

    public String generateAccessToken(UserLogin userLogin, Set<String> permissions) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expirationInSeconds * 1000L);

        return Jwts.builder()
                .setIssuer("Platform")
                .setSubject(userLogin.getId().getValue())
                .claim("firstName", userLogin.getPerson().getFirstName())
                .claim("lastName", userLogin.getPerson().getLastName())
                .claim("organizationId", userLogin.getOrganizationId().getValue())
                .claim("permissions", permissions)
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    public String generateRefreshToken(ID userLoginId) {
        Date expiryDate = Date.from(Instant.now().plus(Duration.ofDays(refreshExpirationInDays)));

        return Jwts.builder()
                .setSubject(userLoginId.getValue())
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .claim("isRefresh", Boolean.TRUE)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }
}
