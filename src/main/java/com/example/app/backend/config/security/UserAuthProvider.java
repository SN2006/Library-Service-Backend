package com.example.app.backend.config.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.example.app.backend.dto.userDtos.UserDto;
import com.example.app.backend.services.UserService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Base64;
import java.util.Date;

@Component
public class UserAuthProvider {

    @Value("${security.jwt.token.secret-key}")
    private String secret;

    private final UserService userService;

    @Autowired
    public UserAuthProvider(UserService userService) {
        this.userService = userService;
    }

    @PostConstruct
    protected void init() {
        secret = Base64.getEncoder().encodeToString(secret.getBytes());
    }

    public String createToken(UserDto userDto){
        Date now = new Date();
        Date validity = new Date(now.getTime() + 2_678_400_000L); // 1 month

        Algorithm algorithm = Algorithm.HMAC256(secret);
        return JWT.create()
                .withSubject(userDto.getEmail())
                .withIssuedAt(now)
                .withExpiresAt(validity)
                .sign(algorithm);
    }

    public Authentication validateToken(String token) {
        Algorithm algorithm = Algorithm.HMAC256(secret);

        JWTVerifier verifier = JWT.require(algorithm)
                .build();

        DecodedJWT decoded= verifier.verify(token);

        UserDto user =userService.findUserByEmail(decoded.getSubject());

        return new UsernamePasswordAuthenticationToken(user, null, Arrays.asList(user.getRole()));
    }
}
