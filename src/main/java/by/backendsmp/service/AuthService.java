package by.backendsmp.service;

import by.backendsmp.config.jwt.JwtTokenProvider;
import by.backendsmp.entity.User;
import by.backendsmp.service.user.UserService;
import by.backendsmp.web.dto.jwt.JwtRequest;
import by.backendsmp.web.dto.jwt.JwtResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    public JwtResponse login(JwtRequest loginRequest) {
        JwtResponse jwtResponse = new JwtResponse();
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUserName(), loginRequest.getPassword()));
        User user = userService.findUserByUserName(loginRequest.getUserName());
        jwtResponse.setId(user.getId());
        jwtResponse.setUserName(user.getUserName());
        jwtResponse.setAccessToken(jwtTokenProvider.createAccessToken(user.getId(), user.getUserName()));
        jwtResponse.setRefreshToken(jwtTokenProvider.createRefreshToken(user.getId(), user.getUserName()));
        return jwtResponse;
    }

    public JwtResponse refresh(String refreshToken) {
        return jwtTokenProvider.refreshUserToken(refreshToken);
    }
}
