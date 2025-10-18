package by.backendsmp.web.dto.jwt;

import lombok.Data;

@Data
public class JwtRequest {
    private String userName;
    private String password;
}
