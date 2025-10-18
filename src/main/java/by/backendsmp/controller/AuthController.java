package by.backendsmp.controller;

import by.backendsmp.entity.User;
import by.backendsmp.service.AuthService;
import by.backendsmp.service.user.UserService;
import by.backendsmp.web.dto.jwt.JwtRefresh;
import by.backendsmp.web.dto.jwt.JwtRequest;
import by.backendsmp.web.dto.UserDTO;
import by.backendsmp.web.dto.jwt.JwtResponse;
import by.backendsmp.web.mappers.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final UserMapper userMapper;
    private final UserService userService;

    @PostMapping("/register")
    public JwtResponse register(@RequestBody UserDTO userDTO) {
        User user = userMapper.toUser(userDTO);
        userService.registerUser(user);
        JwtRequest jwtRequest = new JwtRequest();
        jwtRequest.setUserName(userDTO.getUserName());
        jwtRequest.setPassword(userDTO.getPassword());
        return authService.login(jwtRequest);
    }

    @PostMapping("/log")
    public JwtResponse login(@RequestBody JwtRequest jwtRequest ) {
        return authService.login(jwtRequest);
    }

    @PostMapping("/ref")
    public JwtResponse refreshToken(@RequestBody JwtRefresh token) {
        return authService.refresh(token.getRefreshToken());
    }
}
