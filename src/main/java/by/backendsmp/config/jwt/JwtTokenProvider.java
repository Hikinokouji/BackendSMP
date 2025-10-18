package by.backendsmp.config.jwt;

import by.backendsmp.entity.User;
import by.backendsmp.service.user.UserService;
import by.backendsmp.web.dto.jwt.JwtProperties;
import by.backendsmp.web.dto.jwt.JwtResponse;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class JwtTokenProvider {
    private final JwtProperties jwtProperties;
    private final UserDetailsService userDetailsService;
    private final UserService userService;
    private Key key;

    @PostConstruct
    public void init(){
        this.key = Keys.hmacShaKeyFor(jwtProperties.getSecret().getBytes());
    }

    public String createAccessToken(Long userId,
                                    String userName){
        Date now = new Date();
        Date validity = new Date(now.getTime() + jwtProperties.getAccess());

        return Jwts.builder()
                .subject(userName)
                .claim("userId", userId)
                .issuedAt(now)
                .expiration(validity)
                .signWith(key)
                .compact();
    }

    public String createRefreshToken(Long userId,
                                     String userName) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + jwtProperties.getRefresh());

        return Jwts.builder()
                .subject(userName)
                .claim("userId", userId)
                .issuedAt(now)
                .expiration(validity)
                .signWith(key)
                .compact();
    }

    public JwtResponse refreshUserToken(String refreshToken) {
        JwtResponse jwtResponse = new JwtResponse();
        if(!validatToken(refreshToken)){
            throw new RuntimeException("Не валідний токен");
        }
        Long userId = Long.valueOf(getId(refreshToken));
        User user = userService.getById(userId);
        jwtResponse.setId(userId);
        jwtResponse.setUserName(user.getUserName());
        jwtResponse.setAccessToken(createAccessToken(userId, user.getUserName()));
        jwtResponse.setRefreshToken(createRefreshToken(userId, user.getUserName()));
        return jwtResponse;
    }

    public boolean validatToken(String token) {
        Jws<Claims> claims = Jwts
                .parser()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token);

        return !claims.getBody().getExpiration().before(new Date());
    }

    private String getId(String token){
        return Jwts
                .parser()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("userId")
                .toString();
    }
    private String getUserName(String token){
        return Jwts
                .parser()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public Authentication getAuthentication(String token) {
        String userName = getUserName(token);
        UserDetails userDetails = userDetailsService.loadUserByUsername(getUserName(token));
        return new UsernamePasswordAuthenticationToken(userName, "", userDetails.getAuthorities());
    }

}
